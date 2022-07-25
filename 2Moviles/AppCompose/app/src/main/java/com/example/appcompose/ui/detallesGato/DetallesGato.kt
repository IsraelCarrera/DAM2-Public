package com.example.appcompose.ui.detallesGato

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.appcompose.domain.Gatos
import com.example.appcompose.ui.common.IrAtras
import com.example.appcompose.ui.utils.Constantes
import kotlinx.coroutines.flow.collect

@Composable
fun MasInfoGato(
    onGoBackClick: () -> Unit,
    idGato: Int,
    viewModel: DetallesGatoViewModel = hiltViewModel(),
) {
    //Buscar al gato
    viewModel.handleEvent(DetallesGatoContract.DetallesGatoEvent.BuscarGatoPorId(idGato))
    val gato = viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(DetallesGatoContract.DetallesGatoEvent.ErrorMostrado)
                }
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = Constantes.MAS_INFO + gato.value.gato?.nombre) },
                navigationIcon = { IrAtras(onGoBackClick) }
            )
        }
    ) {
        gato.value.gato?.let { it1 ->
            ImprimirInfo(gato = it1)
        }
    }
}

@Composable
fun ImprimirInfo(
    gato: Gatos,
) {
    Column {
        Image(
            painter = rememberImagePainter(
                data = gato.foto
            ),
            contentDescription = gato.foto,
            modifier = Modifier.size(width = 600.dp, height = 400.dp)

        )
        Text(text = Constantes.NOMBRE_APELLIDOS + gato.nombre + Constantes.ESPACIO + gato.apellidos)
        Text(text = Constantes.DUENOGATO + gato.dueno)
        Text(text = Constantes.EDADGATO + gato.edad)
    }
}