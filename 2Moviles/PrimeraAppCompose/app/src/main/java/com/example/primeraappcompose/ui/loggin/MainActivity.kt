package com.example.primeraappcompose.ui.loggin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.primeraappcompose.ui.theme.PrimeraAppComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrimeraAppComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Probando3()
                }
            }
        }
    }
}


@Composable
fun Probando3() {
    val text = ""
    var texto by rememberSaveable { mutableStateOf(text) }
    val text2 = ""
    var texto2 by rememberSaveable { mutableStateOf(text2) }
    val aviso = ""
    var avisoRemember by rememberSaveable { mutableStateOf(aviso)}
    Probando2(
        text = texto,
        onTextChange = { texto = it },
        text2 = texto2,
        onTextChange2 = { texto2 = it },
        aviso = aviso
    )

}

@Composable
fun Probando2(
    text: String,
    onTextChange: (String) -> Unit,
    text2: String,
    onTextChange2: (String) -> Unit,
    aviso : String
) {
    var avisazo by rememberSaveable { mutableStateOf(aviso) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = onTextChange,
            )
            OutlinedTextField(
                value = text2,
                onValueChange = onTextChange2,
            )
            Button(onClick ={
                avisazo = if(text == "root" && text2 == "root"){
                    "Loggin correcto"
                }else{
                    "Fallazo coño"
                }
            }) {
                Text(text = "Botton logging")
            }
            Text(text = avisazo)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PrimeraAppComposeTheme {
        Probando3()
    }
}