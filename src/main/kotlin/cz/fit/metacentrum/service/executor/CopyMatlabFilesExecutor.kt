package cz.fit.metacentrum.service.executor

import cz.fit.metacentrum.domain.ExecutionMetadata
import cz.fit.metacentrum.domain.MatlabTaskType
import cz.fit.metacentrum.service.api.TaskExecutor
import java.nio.file.Files
import java.nio.file.Paths

/**
 *
 * @author Jakub Tucek
 */
class CopyMatlabFilesExecutor : TaskExecutor {

    override fun execute(metadata: ExecutionMetadata): ExecutionMetadata {
        val matlabAction = metadata.configFile.taskType as MatlabTaskType

        val outPath = metadata.executionOutputPath ?: throw IllegalStateException("Execution path not set")

        Files.copy(
                Paths.get(matlabAction.folder),
                outPath.resolve("matlab")
        )

        return metadata
    }

}