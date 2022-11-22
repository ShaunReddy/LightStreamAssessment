package com.example.shaunreddy.lightstreamassignment.characters.api.localStorage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character

@Dao
interface RickAndMortyCharactersDao {
    @Query("Select * from Character order by name asc")
    suspend fun getCharacters(): List<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newsList: List<Character>)

}