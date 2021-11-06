package com.example.memebase.models.memesModels

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class Data(val memes: ArrayList<Memes>?){

    class TypeConverterData {
        private val gson : Gson = Gson()
        @TypeConverter
        fun stringToSomeObjectList(data: String?) : Data? {
            if(data == null)return null

            val listType: Type = object : TypeToken<Data>() {}.type
            return gson.fromJson(data, listType)
        }

        @TypeConverter
        fun someObjectListToString(someObject: Data?): String?
        {
            return gson.toJson(someObject)
        }
}


}

