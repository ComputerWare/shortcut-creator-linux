import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Preview
@Composable
fun App() {
    var filename by remember { mutableStateOf("") }
    var shortcutTitle by remember { mutableStateOf("") }

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
            OutlinedTextField(
                value = filename,
                onValueChange = { filename = it },
                label = { Text("Enter shortcut file name") },
                placeholder = { Text("Enter shortcut file name") },
                modifier = Modifier
                    .align(Alignment.TopStart).padding(20.dp) // Center the text field within the Box
                    .width(300.dp) // Set a specific width (optional)
                    .padding(16.dp) // Add padding around the text field (optional)
            )

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

            Button(
                modifier = Modifier.align(Alignment.BottomEnd).padding(20.dp),
                onClick = { println("Clicked!!") }
            ) {
                Text("Next")
            }
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
