package com.example.examen2ev.ui.detallespokemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.examen2ev.data.modelo.PokemonRetrofit
import com.example.examen2ev.domain.Pokemon
import com.example.examen2ev.ui.common.IrAtras
import com.example.examen2ev.ui.listarpokemon.PintarPokemons
import kotlinx.coroutines.flow.collect


@Composable
fun MasInfoPokemon(
    onGoBackClick: () -> Unit,
    onNavigate: (String) -> Unit,
    idPokemon: Int,
    viewModel: DetallesPokemonViewModel = hiltViewModel(),
) {
    //Buscar al gato
    viewModel.handleEvent(DetallesPokemonContract.DetallesPokemonEvent.BuscarPokemonPorId(idPokemon))
    val pokemon = viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(DetallesPokemonContract.DetallesPokemonEvent.ErrorMostrado)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = "Lista de pokemons") },
                navigationIcon = { IrAtras(onGoBackClick) },
                actions = {
                    IconButton(onClick = { onNavigate("addPokemon") }) {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = null
                        )
                    }
                    IconButton(onClick = { onNavigate("verPokemons") }) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        pokemon.value.pokemon?.let { it1 ->
            ImprimirInfo(pokemon = it1)
        }
    }
}

@Composable
fun ImprimirInfo(
    pokemon: Pokemon,
) {
    Column {
        Text(text = "ID del pokemon: " + pokemon.id)
        Text(text = "Nombre del pokemon: " + pokemon.nombre)
        Text(text = "Generación del pokemon: " + pokemon.generacion)
        Text(text = "Caracteristicas inutiles: " + pokemon.caracteristicasInutiles)
        Text(text = "Habilidades: ")
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(pokemon.habilidades) { tt ->
                Text(text = tt)
            }
        }
    }
}