package cz.fit.metacentrum.service.action.submit.executor.re

import cz.fit.metacentrum.domain.meta.ExecutionMetadata
import cz.fit.metacentrum.service.api.TaskExecutor

/**
 * Cleans metadata state.
 * @author Jakub Tucek
 */
class CleanStateExecutor : TaskExecutor {
    override fun execute(metadata: ExecutionMetadata): ExecutionMetadata {
        return metadata.copy(currentState = null)
    }
}