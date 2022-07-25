package com.example.pelisyseriesapp.data.sources.remote.di

import com.example.pelisyseriesapp.data.sources.remote.PelisAPI
import com.example.pelisyseriesapp.data.sources.remote.SeriesAPI
import com.example.pelisyseriesapp.data.sources.remote.ServiceInterceptor
import com.example.pelisyseriesapp.data.utils.Constantes.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModulo {
    @Singleton
    @Provides
    fun provideServiceInterceptor(): ServiceInterceptor = ServiceInterceptor()


    @Singleton
    @Provides
    fun provideHttpClient(serviceInterceptor: ServiceInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(serviceInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    //Las interfaces de la api a la que se llama.
    @Singleton
    @Provides
    fun provideCurrencyServiceApiPelis(retrofit: Retrofit): PelisAPI =
        retrofit.create(PelisAPI::class.java)

    @Singleton
    @Provides
    fun provideCurrencyServiceApiSeries(retrofit: Retrofit): SeriesAPI =
        retrofit.create(SeriesAPI::class.java)
}