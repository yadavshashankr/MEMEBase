package com.shashank.memebase.meme.memesModels

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class Data(@SerializedName("memes")
                @Expose val memes: ArrayList<Memes>?){

    object ConverterMemes {
        @TypeConverter
        fun fromString(value: String?) : ArrayList<String> {
            val listType = object : TypeToken<ArrayList<Memes>?>() {}.type
            return Gson().fromJson(value, listType)
        }

        @TypeConverter
        fun fromArrayList(list : ArrayList<Memes>?): String {
            val gson = Gson()
            return gson.toJson(list)
        }
    }


}

