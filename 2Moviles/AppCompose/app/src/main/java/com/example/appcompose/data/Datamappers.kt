package com.example.appcompose.data

import com.example.appcompose.data.modelo.GatitosEntidad
import com.example.appcompose.domain.Gatos
import com.example.appcompose.domain.GatosLista

fun Gatos.toGatosEntidad(): GatitosEntidad{
    return GatitosEntidad(
        nombre= this.nombre,
        apellidos= this.apellidos,
        dueno= this.dueno,
        edad= this.edad,
        foto= this.foto
    )
}

fun GatitosEntidad.toGatosLista(): GatosLista{
    return GatosLista(
        id=this.id,
        foto = this.foto,
        nombre = this.nombre
    )
}

fun GatitosEntidad.toGatos(): Gatos{
    return Gatos(
        id= this.id,
        nombre= this.nombre,
        apellidos= this.apellidos,
        dueno= this.dueno,
        edad= this.edad,
        foto= this.foto
    )
}