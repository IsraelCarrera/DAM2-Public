package com.example.examen2ev.ui.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable

@Composable
fun IrAtras(back: () -> Unit) {
    IconButton(onClick = back) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null
        )
    }
}

@Composable
fun MenuTop(onNavigate: (String) -> Unit){
    TopAppBar(
        title = { Text(text = "Lista de pokemons") },
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