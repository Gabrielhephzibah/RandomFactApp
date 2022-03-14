package com.cherish.randomfactproject.utils

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.cherish.randomfactproject.data.model.Rating
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DataConverter {

    @TypeConverter
    fun fromRatingToString(value: Rating): String{
        val gson = Gson()
        val token = object : TypeToken<Rating>() {}.type
        return gson.toJson(value, token)
    }

    @TypeConverter
    fun fromStringToRating(value: String): Rating{
        val gson = Gson()
        val token = object :TypeToken<Rating>() {}.type
        return gson.fromJson(value, token)
    }


}