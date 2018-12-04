package cz.fit.metacentrum.util


const val defaultCommandDelimiter = "================================================================================"

/**
 * Console writer of status and information for user to have consistent spacing
 * @author Jakub Tucek
 */
object ConsoleWriter {

    fun writeHeader(head: String) {
        println("     $head")
    }

    fun writeStatus(msg: String) {
        println(" --- $msg")
    }

    fun writeListItem(msg: String) {
        println("   * $msg")
    }

    fun writeStatusDetail(msg: String) {
        println(getStatusDetailLine(msg))
    }

    fun getStatusDetailLine(msg: String): String {
        return "          $msg"
    }

    fun writeStatusDone() {
        println("          DONE")
    }

    fun writeDelimiter() {
        println(defaultCommandDelimiter)
    }
}