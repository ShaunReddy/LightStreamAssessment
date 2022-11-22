package com.example.shaunreddy.lightstreamassignment.domain

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.shaunreddy.lightstreamassignment.characters.api.localStorage.RickAndMortyCharactersDao
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.LocationTypeConverter
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.location.api.localStorage.RickAndMortyLocationDao
import com.example.shaunreddy.lightstreamassignment.location.api.modal.ResidentTypeConverter
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocation

@Database(entities = [Character::class, RickAndMortyLocation::class], version=1, exportSchema = false)
@TypeConverters(LocationTypeConverter::class, ResidentTypeConverter::class)
abstract class RickAndMortyDatabase: RoomDatabase() {
    abstract fun getCharactersDao(): RickAndMortyCharactersDao
    abstract fun getLocationsDao(): RickAndMortyLocationDao
    companion object {
        @Volatile
        private var instance: RickAndMortyDatabase? = null

        fun getInstance(context: Context): RickAndMortyDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): RickAndMortyDatabase {

            return Room.databaseBuilder(context, RickAndMortyDatabase::class.java, "rick-morty-db")
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                        }
                    }).build()
        }
    }

}