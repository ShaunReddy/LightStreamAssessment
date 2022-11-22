package com.example.shaunreddy.lightstreamassignment

import android.app.Application
import com.example.shaunreddy.lightstreamassignment.characters.di.rickAndMortyCharactersUsecaseModule
import com.example.shaunreddy.lightstreamassignment.characters.view.di.rickAndMortyRepositoryModule
import com.example.shaunreddy.lightstreamassignment.di.appModule
import com.example.shaunreddy.lightstreamassignment.location.di.rickAndMortyLocationRepositoryModule
import com.example.shaunreddy.lightstreamassignment.location.di.rickAndMortyLocationUseCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class RickAndMortyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@RickAndMortyApp)
            modules(listOf(appModule,
                rickAndMortyRepositoryModule,
                rickAndMortyCharactersUsecaseModule,
                rickAndMortyLocationRepositoryModule,
                rickAndMortyLocationUseCaseModule))
        }
    }
}