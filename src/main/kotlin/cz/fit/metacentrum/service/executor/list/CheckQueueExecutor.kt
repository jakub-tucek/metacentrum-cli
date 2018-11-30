package cz.fit.metacentrum.service.executor.list

import cz.fit.metacentrum.domain.QueueRecord
import cz.fit.metacentrum.domain.meta.*
import cz.fit.metacentrum.service.FailedJobFinderService
import cz.fit.metacentrum.service.QueueService
import cz.fit.metacentrum.service.api.TaskExecutor
import javax.inject.Inject


/**
 * Executor that checks queue for jobs.
 * @author Jakub Tucek
 */
class CheckQueueExecutor : TaskExecutor {

    @Inject
    private lateinit var queueService: QueueService
    @Inject
    private lateinit var failedJobFinderService: FailedJobFinderService

    override fun execute(metadata: ExecutionMetadata): ExecutionMetadata {
        if (checkIfFinishedQueueWasProcessed(metadata)) {
            // no need to check status again
            return metadata
        }
        // extract jobs and path for further usage
        val storagePath = metadata.storagePath
                ?: throw IllegalStateException("Storage path is missing in metadata object")
        val jobs = metadata.jobs ?: throw IllegalStateException("Jobs are missing from metadata object")

        // mapped job by pid so queue records can be mapped to jobs easily
        val mappedJobsByPid = jobs
                .map { (it.pid ?: throw IllegalStateException("Missing pid on task ${it}")) to it }
                .toMap()
        // read queue job records mapped by its state
        val mappedRecordsByState = retrieveQueuedJobs(metadata, mappedJobsByPid)

        val queued = mappedRecordsByState[QueueRecord.State.Q] ?: emptyList()
        val running = mappedRecordsByState[QueueRecord.State.R] ?: emptyList()
        val failedJobs = failedJobFinderService.findFailedJobs(storagePath, jobs)

        // no running jobs - so it either failed or finished successfully
        if (running.isEmpty()) {
            if (failedJobs.isEmpty()) {
                return metadata.copy(state = ExecutionMetadataStateOk)
            } else {
                return metadata.copy(state = ExecutionMetadataStateFailed(
                        failedJobs
                ))
            }
        } else {
            // running task, some jobs maybe finished with OK or fail status
            return metadata.copy(
                    state = ExecutionMetadataStateRunning(
                            runningJobs = mapRecordsToRunningJob(running, mappedJobsByPid),
                            queuedJobs = mapRecordsToRunningJob(queued, mappedJobsByPid),
                            failedJobs = failedJobs
                    )
            )
        }
    }

    private fun mapRecordsToRunningJob(running: List<QueueRecord>, mappedJobsByPid: Map<String, ExecutionMetadataJob>): List<ExecutionMetadataJobRunningWrapper> {
        return running.map {
            val job = mappedJobsByPid[it.pid] ?: throw IllegalStateException("Mapped pid does not exist")
            ExecutionMetadataJobRunningWrapper(
                    job = job,
                    runTime = it.timestamp
            )
        }

    }

    private fun retrieveQueuedJobs(metadata: ExecutionMetadata, mapedJobsByPid: Map<String, ExecutionMetadataJob>): Map<QueueRecord.State, List<QueueRecord>> {
        val username = metadata.submittingUsername ?: throw IllegalStateException("Submitted username is missing")
        val queueList = queueService.retrieveQueueForUser(username)

        return queueList.filter { mapedJobsByPid.containsKey(it.pid) }
                .groupBy { it.state }
    }

    private fun checkIfFinishedQueueWasProcessed(metadata: ExecutionMetadata): Boolean {
        return when (metadata.state) {
            is ExecutionMetadataStateOk -> true
            is ExecutionMetadataStateFailed -> true
            else -> false
        }
    }


}