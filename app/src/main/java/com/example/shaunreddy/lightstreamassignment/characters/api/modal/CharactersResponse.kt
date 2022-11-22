package com.example.shaunreddy.lightstreamassignment.characters.api.modal

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


data class CharactersResponse(
    val results: List<Character>
)

@Parcelize
@Entity(tableName="Character")
data class Character(
    val created: String,
    val gender: String,
    @PrimaryKey
    val id: Int,
    val image: String,
    val location: Location,
    val name: String,
    val origin: Location,
    val species: String,
    val status: String,
    val type: String
) : Parcelable

@Parcelize
data class Location(
    val name: String,
    val url: String
) : Parcelable