package com.example.appcompose.ui.loggin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.appcompose.ui.utils.Constantes


@Composable
fun HacerLoggin(
    onNavigate: (String) -> Unit,
) {
    var rememberUser by rememberSaveable { mutableStateOf("") }
    var rememberPass by rememberSaveable { mutableStateOf("") }
    val aviso = Constantes.VACIO
    checkLoggin(
        textUser = rememberUser,
        onUserChange = { rememberUser = it },
        textPass = rememberPass,
        onPassChange = { rememberPass = it },
        aviso = aviso,
        onNavigate = onNavigate
    )
}

@Composable
fun checkLoggin(
    textUser: String,
    onUserChange: (String) -> Unit,
    textPass: String,
    onPassChange: (String) -> Unit,
    aviso: String,
    onNavigate: (String) -> Unit,

    ) {
    var avisazo by rememberSaveable { mutableStateOf(aviso) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = textUser,
                onValueChange = onUserChange,
                label = { Text(text = Constantes.NOMBRE) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            OutlinedTextField(
                value = textPass,
                onValueChange = onPassChange,
                label = { Text(text = Constantes.PASS) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true
            )
            Button(onClick = {
                if (textUser == Constantes.ROOT && textPass == Constantes.ROOT) {
                    onNavigate(Constantes.DIR_VER_GATOS)
                } else {
                    avisazo = Constantes.NO_LOGGIN
                }
            }) {
                Text(text = Constantes.LOGGIN)
            }
            Text(text = avisazo)
        }
    }
}
