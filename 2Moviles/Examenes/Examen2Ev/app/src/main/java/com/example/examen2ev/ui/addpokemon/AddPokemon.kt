package com.example.examen2ev.ui.addpokemon

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examen2ev.domain.Pokemon
import com.example.examen2ev.ui.common.MenuTop
import kotlinx.coroutines.flow.collect


@Composable
fun AddPokemon(
    onNavigate: (String) -> Unit,
    viewModel: AddPokemonViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState()
    val textNombre = ""
    val rememberNombre = rememberSaveable { mutableStateOf(textNombre) }
    val textGeneracion = ""
    val rememberGeneracion = rememberSaveable { mutableStateOf(textGeneracion) }
    val textCaracteristicasInutiles = ""
    val rememberCaracteristicasInutiles = rememberSaveable { mutableStateOf(textCaracteristicasInutiles) }
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(AddPokemonContract.AddPokemonEvent.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(AddPokemonContract.AddPokemonEvent.MensajeMostrado)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                var pokemon: Pokemon? = null
                if (!rememberGeneracion.value.isEmpty() &&
                    !rememberNombre.value.isEmpty() &&
                    !rememberCaracteristicasInutiles.value.isEmpty()
                ) {
                    pokemon = Pokemon(
                        nombre = rememberNombre.value,
                        generacion = rememberGeneracion.value,
                        caracteristicasInutiles = rememberCaracteristicasInutiles.value,
                        habilidades = emptyList()
                    )
                }
                viewModel.handleEvent(AddPokemonContract.AddPokemonEvent.AddPokemon(pokemon))
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add"
                )
            }
        },
        topBar = {
            MenuTop(onNavigate = onNavigate)
        }
    ) {
        NombreYApellidosGato(
            textNombre = rememberNombre.value,
            textGeneracion = rememberGeneracion.value,
            textCaracteristicasInutiles = rememberCaracteristicasInutiles.value,
            onNombreChange = { rememberNombre.value = it },
            onGeneracionChange = { rememberGeneracion.value = it },
            onCaracteristicasInutilesChange = { rememberCaracteristicasInutiles.value = it },
        )
    }
}


@Composable
fun NombreYApellidosGato(
    textNombre: String,
    textGeneracion: String,
    textCaracteristicasInutiles: String,
    onNombreChange: (String) -> Unit,
    onGeneracionChange: (String) -> Unit,
    onCaracteristicasInutilesChange: (String) -> Unit,
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
                label = { Text(text = "Nombre pokemon") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            OutlinedTextField(
                value = textGeneracion,
                onValueChange = onGeneracionChange,
                label = { Text(text = "Generación") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            OutlinedTextField(
                value = textCaracteristicasInutiles,
                onValueChange = onCaracteristicasInutilesChange,
                label = { Text(text = "Características inutiles") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
        }
    }
}