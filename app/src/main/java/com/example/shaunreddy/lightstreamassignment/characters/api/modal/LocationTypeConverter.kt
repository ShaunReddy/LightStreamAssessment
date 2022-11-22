package com.example.shaunreddy.lightstreamassignment.characters.api.modal

import androidx.room.TypeConverter
import org.json.JSONObject

class LocationTypeConverter {
    @TypeConverter
    fun fromLocation(location: Location): String {
        return JSONObject().apply {
            put("name", location.name)
            put("url", location.url)
        }.toString()
    }

    @TypeConverter
    fun toLocation(location: String): Location {
        return with(JSONObject(location)) {
            Location(name = getString("name"), url = getString("url"))
        }
    }
}