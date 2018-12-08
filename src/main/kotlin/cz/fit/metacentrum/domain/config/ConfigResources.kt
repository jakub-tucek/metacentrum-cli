package cz.fit.metacentrum.domain.config


// defines config resources
data class ConfigResources(
        val profile: ConfigResourceProfile,
        val details: ConfigResourcesDetails? = null,
        val modules: Set<String> = emptySet(),
        val toolboxes: Set<String> = emptySet()
)

data class ConfigResourcesDetails(
        val chunks: Int,
        val walltime: String,
        val mem: String,
        val ncpus: Int,
        val scratchLocal: String,
        val ngpus: Int? = null
)

enum class ConfigResourceProfile {
    CUSTOM
}

