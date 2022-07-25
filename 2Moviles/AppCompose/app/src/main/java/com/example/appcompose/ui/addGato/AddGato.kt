package com.example.appcompose.ui.addGato

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appcompose.domain.Gatos
import com.example.appcompose.ui.common.IrAtras
import com.example.appcompose.ui.utils.Constantes
import kotlinx.coroutines.flow.collect


@Composable
fun AddGato(
    onGoBackClick: () -> Unit,
    viewModel: AddGatoViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val textNombre = Constantes.VACIO
    var rememberNombre by rememberSaveable { mutableStateOf(textNombre) }
    val textApellido = Constantes.VACIO
    var rememberApellido by rememberSaveable { mutableStateOf(textApellido) }
    val textDueno = Constantes.VACIO
    var rememberDueno by rememberSaveable { mutableStateOf(textDueno) }
    val textEdad = Constantes.VACIO
    var rememberEdad by rememberSaveable { mutableStateOf(textEdad) }
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(AddGatoContract.AddGatoEvent.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(AddGatoContract.AddGatoEvent.MensajeMostrado)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                var gato: Gatos? = null
                if (!rememberApellido.isEmpty() && !rememberNombre.isEmpty()
                    && !rememberDueno.isEmpty() && !rememberEdad.isEmpty() && rememberEdad.isDigitsOnly()
                ) {
                    gato = Gatos(
                        nombre = rememberNombre,
                        apellidos = rememberApellido,
                        dueno = rememberDueno,
                        edad = rememberEdad.toInt(),
                    )
                }
                viewModel.handleEvent(AddGatoContract.AddGatoEvent.AddGato(gato))
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = Constantes.ADD
                )
            }
        },
        topBar = {
            TopAppBar(
                title = { Text(text = Constantes.ADD_GATOS) },
                navigationIcon = { IrAtras(onGoBackClick) }
            )
        }
    ) {
        NombreYApellidosGato(
            textNombre = rememberNombre,
            textApellido = rememberApellido,
            textDueno = rememberDueno,
            textEdad = rememberEdad,
            onNombreChange = { rememberNombre = it },
            onApellidoChange = { rememberApellido = it },
            onDuenoChange = { rememberDueno = it },
            onEdadChange = { rememberEdad = it },
        )
    }
}


@Composable
fun NombreYApellidosGato(
    textNombre: String,
    textApellido: String,
    textDueno: String,
    textEdad: String,
    onNombreChange: (String) -> Unit,
    onApellidoChange: (String) -> Unit,
    onDuenoChange: (String) -> Unit,
    onEdadChange: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {
            OutlinedTextField(
                value = textNombre,
                onValueChange = onNombreChange,
                label = { Text(text = Constantes.NOMBRE) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            OutlinedTextField(
                value = textApellido,
                onValueChange = onApellidoChange,
                label = { Text(text = Constantes.APELLIDO) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            OutlinedTextField(
                value = textDueno,
                onValueChange = onDuenoChange,
                label = { Text(text = Constantes.DUENO) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            OutlinedTextField(
                value = textEdad,
                onValueChange = onEdadChange,
                label = { Text(text = Constantes.EDAD) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1
            )
        }
    }
}