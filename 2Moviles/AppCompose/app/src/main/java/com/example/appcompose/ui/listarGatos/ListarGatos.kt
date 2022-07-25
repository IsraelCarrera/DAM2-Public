package com.example.appcompose.ui.listarGatos

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.appcompose.domain.GatosLista
import com.example.appcompose.ui.common.IrAtras
import com.example.appcompose.ui.utils.Constantes
import kotlinx.coroutines.flow.collect

@Composable
fun ListarGatos(
    onNavigate: (String) -> Unit,
    onGoBackClick: () -> Unit,
    viewModel: ListarGatosViewModel = hiltViewModel(),
) {
    val listGatitos = viewModel.uiState.collectAsState()
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true) {
        viewModel.uiState.collect { value ->
            value.error.let {
                value.error?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(ListarGatosContract.ListarGatosEvent.ErrorMostrado)
                }
            }
            value.mensaje.let {
                value.mensaje?.let { it1 ->
                    scaffoldState.snackbarHostState.showSnackbar(it1)
                    viewModel.handleEvent(ListarGatosContract.ListarGatosEvent.MensajeMostrado)
                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text(text = Constantes.LISTAGATOS) },
                actions = {
                    IconButton(onClick = { onNavigate(Constantes.DIR_ADD_GATOS) }) {
                        Icon(
                            imageVector = Icons.Default.PersonAdd,
                            contentDescription = null
                        )
                    }
                },
                navigationIcon = { IrAtras(onGoBackClick) }
            )
        }
    ) {
        VerGatos(onNavigate = onNavigate,
            listaGatitos = listGatitos,
            viewModel = viewModel)
    }
}

@Composable
fun VerGatos(
    onNavigate: (String) -> Unit,
    listaGatitos: State<ListarGatosContract.ListarGatosState>,
    viewModel: ListarGatosViewModel,
) {
    //Borrar
    Column(modifier = Modifier.fillMaxHeight()) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
        ) {
            items(listaGatitos.value.gatos) { tt ->
                PintarGatos(gato = tt, onNavigate = onNavigate, viewModel = viewModel)
            }
        }
    }
}

@Composable
fun PintarGatos(
    gato: GatosLista,
    onNavigate: (String) -> Unit,
    viewModel: ListarGatosViewModel,
) {
    Row(modifier = Modifier
        .size(width = 400.dp, height = 100.dp)
        .clickable {
            onNavigate(Constantes.IR_VERMAS + gato.id)
        }
    ) {
        Image(
            painter = rememberImagePainter(
                data = gato.foto,
                builder = {
                    transformations(CircleCropTransformation())
                }
            ),
            contentDescription = gato.foto,
            modifier = Modifier.fillMaxHeight()
        )
        Column {
            Text(
                text = gato.nombre,
                modifier = Modifier.paddingFromBaseline(top = 50.dp)
            )

        }
    }
    Column(horizontalAlignment = Alignment.End) {
        Button(onClick = {
            gato.id?.let {
                ListarGatosContract.ListarGatosEvent.DeleteGato(it)
            }?.let { viewModel.handleEvent(it) }
        }) {
            Text(text = Constantes.DELETE, color = Color.Red)
        }
    }
}