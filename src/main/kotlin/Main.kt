package org.computerware

import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

fun clearScreen() {
    print("\u001b[H\u001b[2J")
}
fun makeDelay() {
    clearScreen()
    Thread.sleep(500)
}
fun main() {
    print("Enter desktop shortcut file name: ") // shortcut name
    val namefile = readlnOrNull()
    Thread.sleep(2000)
    print("$namefile accepted..")
    clearScreen()

    print("Enter icon path: ") // shortcut icon
    val iconpath = readlnOrNull()
    makeDelay()
    println("$iconpath accepted...")
    makeDelay()

    print("Enter generic name: ") // shortcut generic name
    val genericname = readlnOrNull()
    println("$genericname...")
    makeDelay()

    print("Command to run on shortcut launch: ") // Command to run on launch
    val cmd = readlnOrNull()
    makeDelay()
    print("$cmd...")
    makeDelay()

    print("Open terminal on launch (true or false): ") // Command to run on launch
    val terminalboolean = readlnOrNull()
    makeDelay()
    print("$terminalboolean...")
    makeDelay()
    println("$name, $iconpath, $genericname, $cmd") // Final check
    Thread.sleep(100)

    fun copyFile(sourcePath: String, destinationPath: String) {
        try {
            val source = Paths.get(sourcePath)
            val destination = Paths.get(destinationPath)

            // 1. Check if the destination file exists
            if (!Files.exists(destination)) {
                // 2. Create the destination file if it doesn't exist (and any necessary parent directories)
                val destinationParent = destination.parent
                if (destinationParent != null) {
                    Files.createDirectories(destinationParent) // Create parent directories if needed
                }
                Files.createFile(destination) // Create the file itself
                println("Destination file created.")
            }

            // 3. Copy the file (now the destination file is guaranteed to exist)
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING)
            println("File copied successfully.")

        } catch (e: Exception) {
            println("Error copying file: ${e.message}")
        }
    }

    fun writeShortCutFile() {
        val homeDirectory = System.getProperty("user.home")
        val desktopFile = File("$namefile.desktop")
        var shortcutFinal = """
        |[Desktop Entry]
        |Type=Application
        |Name=$name
        |GenericName=$genericname
        |Icon=$iconpath
        |Exec=$cmd
        |Terminal=$terminalboolean
        """.trimMargin()
        desktopFile.writeText(shortcutFinal)
        copyFile("$namefile.desktop", "$homeDirectory/Desktop/$namefile.desktop")

    }

    writeShortCutFile()
    println("Finished!!")
    Thread.sleep(1000)

}
