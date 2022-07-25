package com.example.examen2ev.ui.listarpokemon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examen2ev.domain.PokemonList
import com.example.examen2ev.ui.addpokemon.AddPokemonViewModel
import com.example.examen2ev.ui.common.MenuTop
import kotlinx.coroutines.flow.collect


@Composable
fun ListarPokemon(
    onNavigate: (String) -> Unit,
    viewModel: ListarPokemonsViewModel = hiltViewModel(),
) {
    val textGeneracion = ""
    val rememberGeneracion = rememberSaveable { mutableStateOf(textGeneracion) }

    val listPokemons = viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(ListarPokemonsContract.ListarPokemonsEvent.ErrorMostrado)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MenuTop(onNavigate)
        }
    ) {
        VerGatos(
            onNavigate = onNavigate,
            listPokemons = listPokemons,
            textGeneracion =rememberGeneracion.value,
            onGeneracionChange = { rememberGeneracion.value = it },
            viewModel = viewModel
        )
    }
}

@Composable
fun VerGatos(
    onNavigate: (String) -> Unit,
    listPokemons: State<ListarPokemonsContract.ListarPokemonsState>,
    textGeneracion: String,
    onGeneracionChange: (String) -> Unit,
    viewModel: ListarPokemonsViewModel
) {
    Column(modifier = Modifier.fillMaxHeight()) {
        Row {
            OutlinedTextField(
                value = textGeneracion,
                onValueChange = onGeneracionChange,
                label = { Text(text = "Filtrar por generacion") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            Button(onClick = { //Meter la consulta de la generacion
                val gen: String? =null
                if(textGeneracion.isBlank()){
                    viewModel.handleEvent(ListarPokemonsContract.ListarPokemonsEvent.GetPokemonGeneracion(gen))
                }
                 }) {
                Text(text = "Consultar gen.", color = Color.White)
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(listPokemons.value.pokemons) { tt ->
                PintarPokemons(pokemon = tt, onNavigate = onNavigate)
            }
        }
    }
}

@Composable
fun PintarPokemons(
    pokemon: PokemonList,
    onNavigate: (String) -> Unit,
) {
    Row(modifier = Modifier
        .size(width = 400.dp, height = 100.dp)
        .clickable {
            onNavigate("pokemon/" + pokemon.id)
        }
    ) {
        Text(
            text = pokemon.nombre,
            modifier = Modifier.paddingFromBaseline(top = 50.dp)
        )
    }
}