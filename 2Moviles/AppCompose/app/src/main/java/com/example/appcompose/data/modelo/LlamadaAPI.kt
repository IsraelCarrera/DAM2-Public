package com.example.appcompose.data.modelo

import retrofit2.Response

abstract class LlamadaAPI {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ResultadoLlamada<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ResultadoLlamada.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
}