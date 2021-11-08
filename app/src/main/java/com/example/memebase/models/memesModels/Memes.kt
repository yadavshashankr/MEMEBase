package com.example.memebase.models.memesModels

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class Memes(val id: String?, val name: String?, val url:String?, val width: Int?,
                 val height: String?, val box_count: Int?){

    class TypeConverterMemes {
        private val gson : Gson = Gson()
        @TypeConverter
        fun stringToSomeObjectList(data: String?) : Memes? {
            if(data == null)return null
            val listType: Type = object : TypeToken<Memes?>() {}.type
            return gson.fromJson<Memes?>(data, listType)
        }

        @TypeConverter
        fun someObjectListToString(someObject: Memes?): String?
        {
            return gson.toJson(someObject)
        }
    }
}
