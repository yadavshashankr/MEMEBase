package com.shashank.memebase.entry.data

import android.content.Context
import com.google.gson.Gson
import com.shashank.memebase.R
import com.shashank.memebase.entry.models.AuthenticationResponse
import com.shashank.memebase.entry.models.RegisterRequest
import javax.inject.Inject

class UserPreferencesImpl @Inject constructor(val context: Context) : UserPreferences {

    private val sharedPreference by lazy { context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)}

    override fun saveAuthenticatedUser(authenticatedUser: AuthenticationResponse?) {
        val jsonAuthenticatedUser = Gson().toJson(authenticatedUser)
        sharedPreference.edit().putString("authUser", jsonAuthenticatedUser).apply()
    }

    override fun getAuthenticatedUser(): AuthenticationResponse? {
        val authenticatedUserJson = sharedPreference.getString("authUser", "")
        return if(!authenticatedUserJson.equals("") && !authenticatedUserJson.equals("null")){
            Gson().fromJson(authenticatedUserJson, AuthenticationResponse::class.java)
        }else{
            AuthenticationResponse("","","")
        }
    }

    override fun verifyUserCredentials(email: String, password: String): Boolean {
        return sharedPreference.getString("email","").equals(email) && sharedPreference.getString("password","").equals(password)
    }

    override fun clearPreferences() {
        sharedPreference.edit().clear().apply()
    }

    override fun saveUser(registerRequest: RegisterRequest) : Boolean {
        sharedPreference.edit().putString("name", registerRequest.fullName).apply()
        sharedPreference.edit().putString("email", registerRequest.email).apply()
        sharedPreference.edit().putString("password", registerRequest.password).apply()
        return true
    }

    override fun isUserLoggedIn(): Boolean {
        return !sharedPreference.getString("name","").equals("") && !sharedPreference.getString("email","").equals("") && !sharedPreference.getString("password","").equals("")
    }
}