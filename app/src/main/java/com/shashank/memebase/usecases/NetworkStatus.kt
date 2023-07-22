package com.shashank.memebase.usecases

sealed class NetworkStatus(){
    object Available : NetworkStatus()
    object Unavailable : NetworkStatus()
}
