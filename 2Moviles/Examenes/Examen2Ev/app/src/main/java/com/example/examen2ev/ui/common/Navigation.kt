package com.example.examen2ev.ui.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.examen2ev.ui.addpokemon.AddPokemon
import com.example.examen2ev.ui.detallespokemon.MasInfoPokemon
import com.example.examen2ev.ui.listarpokemon.ListarPokemon


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        //En listar Pokemon
        startDestination = "verPokemons"
    ) {
        //Ir a Add pokemon
        composable("addPokemon") {
            AddPokemon(onNavigate = { navController.navigate(it) })
        }
        //Ir a listar pokemons
        composable("verPokemons") {
            ListarPokemon(onNavigate = { navController.navigate(it) })
        }
        //Ir a pokemon Detalles
        composable(
            route = "pokemon/{idPokemon}",
            arguments = listOf(
                navArgument("idPokemon") {
                    type = NavType.IntType
                })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("idPokemon")
            requireNotNull(id)
            MasInfoPokemon(onGoBackClick = { navController.popBackStack() },
                onNavigate = { navController.navigate(it) },
                idPokemon = id)
        }
    }
}