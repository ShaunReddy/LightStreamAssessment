package com.example.shaunreddy.lightstreamassignment.location.api.localStorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocation


@Dao
interface RickAndMortyLocationDao {

    @Query("Select * from Location order by name asc")
    suspend fun getLocations(): List<RickAndMortyLocation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newsList: List<RickAndMortyLocation>)

}