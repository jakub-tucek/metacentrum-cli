package cz.fit.metacentrum.service.api

import cz.fit.metacentrum.domain.ConfigFile
import cz.fit.metacentrum.domain.ExecutionResult

/**
 * ActionExecutor is interface that defines and performs one execution step while running some action.
 * @author Jakub Tucek
 */
interface ActionExecutor {

    /**
     * Performs execution based on given valid configuration file.
     */
    fun execute(configFile: ConfigFile): ExecutionResult

}