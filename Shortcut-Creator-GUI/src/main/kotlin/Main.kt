import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption


@Preview
@Composable
fun App() {
    var fileName by remember { mutableStateOf("") }
    var shortcutTitle by remember { mutableStateOf("") }
    var iconPath by remember { mutableStateOf("") }
    var cmdExc by remember { mutableStateOf("") }
    var terminalBoolean by remember { mutableStateOf("false") }
    var appState by remember { mutableStateOf("") }

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
        val desktopFile = File("$fileName.desktop")
        var shortcutFinal = """
        |[Desktop Entry]
        |Type=Application
        |Name=$shortcutTitle
        |GenericName=null
        |Icon=$iconPath
        |Exec=$cmdExc
        |Terminal=$terminalBoolean
        """.trimMargin()
        desktopFile.writeText(shortcutFinal)
        copyFile("$fileName.desktop", "$homeDirectory/Desktop/$fileName.desktop")
        appState = "Shortcut created on Desktop!!"

    }

    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize() // Or specific size
                .background(Color.White) // Optional background
        ) {
            Text(
                text = "Welcome to the desktop shortcut creator",
                modifier = Modifier.align(Alignment.TopCenter).padding(10.dp)
            )
            Spacer(modifier = Modifier.width(20.dp).align(Alignment.TopCenter).padding(20.dp))

            // Box for shortcut file name
            OutlinedTextField(
                value = fileName,
                onValueChange = { fileName = it },
                label = { Text("Enter shortcut file name") },
                placeholder = { Text("Enter shortcut file name") },
                modifier = Modifier
                    .align(Alignment.TopStart).padding(20.dp) // Center the text field within the Box
                    .width(300.dp) // Set a specific width (optional)
                    .padding(16.dp) // Add padding around the text field (optional)
            )

            // Box for shortcut cover title/name
            OutlinedTextField(
                value = shortcutTitle,
                onValueChange = { shortcutTitle = it },
                label = { Text("Enter shortcut cover title") },
                placeholder = { Text("Enter shortcut cover title") },
                modifier = Modifier
                    .align(Alignment.TopEnd).padding(20.dp) // Center the text field within the Box
                    .width(300.dp) // Set a specific width (optional)
                    .padding(16.dp)
            )

            // Box for icon path
            OutlinedTextField(
                value = iconPath,
                onValueChange = { iconPath = it },
                label = { Text("Enter icon path for cover") },
                placeholder = { Text("Enter icon path for cover") },
                modifier = Modifier
                    .align(Alignment.CenterStart).padding(20.dp) // Center the text field within the Box
                    .width(300.dp) // Set a specific width (optional)
                    .padding(16.dp)
            )

            // Box for command to launch
            OutlinedTextField(
                value = cmdExc,
                onValueChange = { cmdExc = it },
                label = { Text("Enter cmd to run on launch") },
                placeholder = { Text("Enter cmd to run on launch") },
                modifier = Modifier
                    .align(Alignment.CenterEnd).padding(20.dp) // Center the text field within the Box
                    .width(300.dp) // Set a specific width (optional)
                    .padding(16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text="Open terminal on launch?",
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = terminalBoolean == "true",
                        onClick = { terminalBoolean = "true" }
                    )
                    Text("True")
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = terminalBoolean == "false",
                        onClick = { terminalBoolean = "false" }
                    )
                    Text("False")
                }
            }
            Button(
                modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp),
                onClick = {
                    writeShortCutFile()
                }
            ) {
                Text("Create")
            }
            Text(
                text = appState,
                modifier = Modifier.align(Alignment.BottomCenter).padding(20.dp)
            )
        }
    }
}

fun main() = application {
    Window(
        title = "Shortcut Creator",
        onCloseRequest = ::exitApplication,
        icon = painterResource("icon.ico")
    ) {
        App()
    }
}
