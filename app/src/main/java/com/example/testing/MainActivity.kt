package com.example.testing

import android.os.Bundle
import android.service.autofill.OnClickAction
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.launch
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testing.ui.theme.BotonesGen
import com.example.testing.ui.theme.TestingTheme
import com.example.testing.ui.theme.textoGeneral
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()

            TestingTheme {
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Greeting(name = "APP")

                        AppButton(
                            buttonText = "Púlsame",
                            onClickAction = {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Gracias por pulsar el botón")
                                }
                            }
                        )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        color = MaterialTheme.colorScheme.onBackground,
        text = "Bienvenido a $name",
        modifier = modifier.padding(16.dp)


    )

}

@Composable
fun AppButton(buttonText: String, modifier: Modifier = Modifier, onClickAction: () -> Unit) {

    Button(
        onClick = onClickAction,
        modifier = modifier,

        colors = ButtonDefaults.buttonColors(
            containerColor = BotonesGen,

        )
    ){
        Text(
            text = buttonText,
            color = textoGeneral
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TestingTheme {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Greeting("Android")
            AppButton("Púlsame", onClickAction = {})
        }
    }
}

}
