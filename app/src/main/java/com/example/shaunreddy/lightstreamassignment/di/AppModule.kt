package com.example.shaunreddy.lightstreamassignment.di

import android.content.Context
import com.example.shaunreddy.lightstreamassignment.BuildConfig
import com.example.shaunreddy.lightstreamassignment.domain.RickAndMortyDatabase
import com.example.shaunreddy.lightstreamassignment.domain.ServiceInterface
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), BuildConfig.BASE_URL) }
    single { provideApiService(get()) }
    single { provideDatabase(androidContext()) }
}

private fun provideOkHttpClient() = OkHttpClient
    .Builder()
    .build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

private fun provideApiService(retrofit: Retrofit): ServiceInterface =
    retrofit.create(ServiceInterface::class.java)

private fun provideDatabase(context: Context): RickAndMortyDatabase = RickAndMortyDatabase.getInstance(context)