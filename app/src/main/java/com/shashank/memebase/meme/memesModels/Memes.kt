package com.shashank.memebase.meme.memesModels

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.TypeConverter
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

data class Memes(@SerializedName("id")
                 @Expose var id: String?, @SerializedName("name")
@Expose var name: String?, @SerializedName("url")
@Expose var url:String?, @SerializedName("width")
@Expose var width: Int?,
                 @SerializedName("height")
                 @Expose var height: String?, @SerializedName("box_count")
                 @Expose var box_count: Int?, @SerializedName("captions")
                 @Expose var captions: Int?){

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