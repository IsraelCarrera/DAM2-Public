package com.example.examen2ev.data.modelo

import retrofit2.Response

abstract class LlamadaAPI {
    //Para el mapeo
    suspend fun <T, R> safeApiCall(
        apiCall: suspend () -> Response<R>,
        transform: (R) -> T
    ): ResultadoLlamada<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ResultadoLlamada.Success(transform(body))
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): ResultadoLlamada<T> =
        ResultadoLlamada.Error("Api call failed $errorMessage")

}