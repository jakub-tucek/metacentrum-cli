package cz.fit.metacentrum.domain.meta

import java.nio.file.Path

/**
 *
 * @author Jakub Tucek
 */
data class ExecutionMetadataScriptRun(val scriptPath: Path,
                                      val runId: Int, // identical to iteration combination index
                                      val pid: String? = null
)