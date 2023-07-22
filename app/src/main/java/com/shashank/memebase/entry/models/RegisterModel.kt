package com.shashank.memebase.entry.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterRequest(@Expose @SerializedName("fullName")var fullName : String,
                           @Expose @SerializedName("email")var email : String,
                           @Expose @SerializedName("password")var password : String){

    override fun toString(): String {
        return "fullName:${fullName}, email:${email}, password:${password}"
    }
}
