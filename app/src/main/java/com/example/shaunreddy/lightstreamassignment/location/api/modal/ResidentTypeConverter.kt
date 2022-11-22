package com.example.shaunreddy.lightstreamassignment.location.api.modal

import androidx.room.TypeConverter
import org.json.JSONArray

class ResidentTypeConverter {
    @TypeConverter
    fun fromResidents(residents: List<Int>): String {
        return JSONArray(residents).toString()
    }

    @TypeConverter
    fun toResidents(residents: String): List<Int> {
        val list = ArrayList<Int>()
        val jsonArray = JSONArray(residents)
        for (i in 0 until jsonArray.length()) {
            list.add(jsonArray.getInt(i))
        }
        return list
    }

}