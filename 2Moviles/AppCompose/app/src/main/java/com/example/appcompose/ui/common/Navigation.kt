package com.example.appcompose.ui.common

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.appcompose.ui.addGato.AddGato
import com.example.appcompose.ui.listarGatos.ListarGatos
import com.example.appcompose.ui.loggin.HacerLoggin
import com.example.appcompose.ui.detallesGato.MasInfoGato
import com.example.appcompose.ui.utils.Constantes


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Constantes.DIR_LOGGIN
    ) {
        composable(Constantes.DIR_LOGGIN) {
            HacerLoggin(onNavigate = { navController.navigate(it) })
        }
        composable(Constantes.DIR_ADD_GATOS) {
            AddGato(onGoBackClick = { navController.popBackStack() })
        }
        composable(Constantes.DIR_VER_GATOS) {
            ListarGatos(onNavigate = { navController.navigate(it) },
                onGoBackClick = { navController.popBackStack() })
        }
        composable(
            route = Constantes.DIR_MASINFO,
            arguments = listOf(
                navArgument(Constantes.IDGATO) {
                    type = NavType.IntType
                })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt(Constantes.IDGATO)
            requireNotNull(id)
            MasInfoGato(onGoBackClick = { navController.popBackStack() }, idGato = id)
        }
    }
}