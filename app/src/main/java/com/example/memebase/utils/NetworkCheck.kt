package com.example.memebase.utils

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity

class NetworkCheck {

    companion object {

        fun verifyAvailableNetwork(activity: AppCompatActivity): Boolean {
            val connectivityManager =
                activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetwork
            return networkInfo != null && networkInfo == connectivityManager.activeNetwork
        }
    }
}