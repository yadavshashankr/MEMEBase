package com.shashank.memebase.entry.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class AuthenticationRequest(@Expose @SerializedName("email")var email : String,
                                 @Expose @SerializedName("password")var password : String)

data class AuthenticationResponse(@Expose @SerializedName("token")var token : String?,
                                  @Expose @SerializedName("userId")var userId : String?,
                                  @Expose @SerializedName("fullName")var fullName : String?)
