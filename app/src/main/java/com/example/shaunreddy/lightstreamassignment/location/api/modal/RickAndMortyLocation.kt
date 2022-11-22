package com.example.shaunreddy.lightstreamassignment.location.api.modal

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "Location")
data class RickAndMortyLocation(
    val dimension: String,
    @PrimaryKey
    val id: Int,
    val name: String,
    val residents: List<Int>,
    val type: String,
): Parcelable
