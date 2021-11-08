package com.example.memebase.models.memesModels

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.TypeConverter
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class Memes(var id: String, var name: String, var url:String, var width: Int,
                 var height: String, var box_count: Int){

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
@BindingAdapter("android:loadImageDataCir")
fun loadImageDataCir(imageView: ImageView, url: String){
    Glide.with(imageView)
        .load(url)
        .circleCrop()
        .into(imageView)
}

@BindingAdapter("android:loadImageData")
fun loadImageData(imageView: ImageView, url: String){
    Glide.with(imageView)
        .load(url)
        .into(imageView)
}