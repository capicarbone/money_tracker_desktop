import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit

fun String.runCommand(workingDir: File): String? {
    try {
        val parts = this.split("\\s".toRegex())
        val proc = ProcessBuilder(*parts.toTypedArray())
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
        proc.waitFor(60, TimeUnit.MINUTES)
        return proc.inputStream.bufferedReader().readText()
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }
}

abstract class CommandsRunner(val workingDir: File, val rootCommand: List<String>) {

    fun runCommand(tokenList: List<String>) : String {
        val tokens = rootCommand.plus(tokenList)
        println(tokens)
        val proc = ProcessBuilder(tokens)
            .directory(workingDir)
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
        proc.waitFor(60, TimeUnit.MINUTES)
        return proc.inputStream.bufferedReader().readText()
    }
}

class MTAccountsCollection(workingDir: File, val source: String) : CommandsRunner(workingDir, listOf("./money_tracker", "accounts")
    ) {
    fun create(name: String) : String = runCommand(listOf("add", name, "--source", source))
    fun list() : String = runCommand(listOf("list","--source", source))
}


class MoneyTrackerCLI(scriptLocation: File, source: String) {

    val accounts = MTAccountsCollection(scriptLocation, source)

}