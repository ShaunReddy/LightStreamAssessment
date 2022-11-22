package com.example.shaunreddy.lightstreamassignment.location.di

import com.example.shaunreddy.lightstreamassignment.domain.RickAndMortyDatabase
import com.example.shaunreddy.lightstreamassignment.location.repository.RickAndMortyLocationLocalRepository
import com.example.shaunreddy.lightstreamassignment.location.repository.RickAndMortyLocationLocalRepositoryImpl
import com.example.shaunreddy.lightstreamassignment.location.repository.RickAndMortyLocationRepository
import com.example.shaunreddy.lightstreamassignment.location.repository.RickAndMortyLocationRepositoryImpl
import org.koin.dsl.module

val rickAndMortyLocationRepositoryModule = module {
    single<RickAndMortyLocationRepository> {RickAndMortyLocationRepositoryImpl(get())}
    single<RickAndMortyLocationLocalRepository> {RickAndMortyLocationLocalRepositoryImpl(get<RickAndMortyDatabase>().getLocationsDao())}
}