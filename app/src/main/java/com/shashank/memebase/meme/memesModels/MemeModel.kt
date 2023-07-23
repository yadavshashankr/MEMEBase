package com.shashank.memebase.meme.memesModels


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.shashank.memebase.globals.Constants
import java.lang.reflect.Type


@Entity(tableName = Constants.ApiProperties.TABLE_NAME_MEME_MODEL)
data class MemeModel(@PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id")val id: Int = 0,
                     @ColumnInfo(name = "data") @SerializedName("data")
                     @Expose val data: Data?
){
    class TypeConverterData {
        private val gson : Gson = Gson()
        @TypeConverter
        fun stringToSomeObjectList(data: String?) : Data? {
            if(data == null)return null

            val listType: Type = object : TypeToken<Data?>() {}.type
            return gson.fromJson(data, listType)
        }

        @TypeConverter
        fun someObjectListToString(someObject: Data?): String?
        {
            return gson.toJson(someObject)
        }
    }
}












