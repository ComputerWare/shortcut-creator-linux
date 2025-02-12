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
    print("Enter desktop shortcut name: ") // shortcut name
    val name = readlnOrNull()
    Thread.sleep(2000)
    print("$name accepted..")
    clearScreen()

    print("Enter desktop shortcut file name: ") // shortcut name
    val name_file = readlnOrNull()
    Thread.sleep(2000)
    print("$name_file accepted..")
    clearScreen()

    print("Enter icon path: ") // shortcut icon
    val icon_path = readlnOrNull()
    makeDelay()
    println("$icon_path accepted...")
    makeDelay()

    print("Enter generic name: ") // shortcut generic name
    val generic_name = readlnOrNull()
    println("$generic_name...")
    makeDelay()

    print("Command to run on shortcut launch: ") // Command to run on launch
    val cmd = readlnOrNull()
    makeDelay()
    print("$cmd...")
    makeDelay()

    print("Open terminal on launch (true or false): ") // Command to run on launch
    val terminal_boolean = readlnOrNull()
    makeDelay()
    print("$terminal_boolean...")
    makeDelay()
    println("$name, $icon_path, $generic_name, $cmd") // Final check
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
        val desktopFile = File("$name_file.desktop")
        var shortcutFinal = """
        |[Desktop Entry]
        |Type=Application
        |Name=$name
        |GenericName=$generic_name
        |Icon=$icon_path
        |Exec=$cmd
        |Terminal=$terminal_boolean
        """.trimMargin()
        desktopFile.writeText(shortcutFinal)
        copyFile("$name_file.desktop", "$homeDirectory/Desktop/$name_file.desktop")

    }

    writeShortCutFile()
    println("Finished!!")
    Thread.sleep(1000)

}