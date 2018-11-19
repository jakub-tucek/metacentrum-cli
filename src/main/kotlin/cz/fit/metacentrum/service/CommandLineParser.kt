package cz.fit.metacentrum.service

import cz.fit.metacentrum.domain.ArgumentInput
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Parser of command line arguments
 */
class CommandLineParser {

    fun parseArguments(args: Array<String>): ArgumentInput {
        val iterator = args.iterator()

        val parameters = mutableMapOf<String, String>()

        while (iterator.hasNext()) {
            val value = iterator.next()
            when (value) {
                "-i", "--i" -> parameters["inputFile"] = retrieveNextValue(iterator)
                "-c", "--c" -> parameters["configFile"] = retrieveNextValue(iterator)
            }
        }


        return ArgumentInput(
                parameters["inputFile"] ?: throw IllegalArgumentException("Input file not given"),
                parameters["configFile"]
        )
    }

    private fun retrieveNextValue(iterator: Iterator<String>): String {
        if (!iterator.hasNext()) {
            val msg = "Parsing arguments failed, value after argument expected but none was found"
            logger.error(msg)
            throw IllegalArgumentException(msg)
        }
        return iterator.next()
    }
}