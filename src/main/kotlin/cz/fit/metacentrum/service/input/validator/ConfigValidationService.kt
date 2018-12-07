package cz.fit.metacentrum.service.input.validator

import cz.fit.metacentrum.domain.ValidationResult
import cz.fit.metacentrum.domain.config.ConfigFile
import javax.inject.Inject


class ConfigValidationService {

    @Inject
    private lateinit var iterationConfigValidator: IterationConfigValidator

    fun validate(configFile: ConfigFile): ValidationResult {
        return listOf(iterationConfigValidator::validate)
                .map { it(configFile) }
                .reduce { a1, a2 -> a1 + a2 }
    }
}