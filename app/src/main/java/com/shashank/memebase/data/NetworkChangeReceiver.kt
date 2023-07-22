package com.shashank.memebase.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import com.shashank.memebase.usecases.NetworkStatus
import javax.inject.Inject


class NetworkChangeReceiver @Inject constructor(var context: Context) : LiveData<NetworkStatus>() {

    val validNetworkConnections : MutableSet<Network> = HashSet()
    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    private var networkCallback: ConnectivityManager.NetworkCallback = object : ConnectivityManager.NetworkCallback() {

        override fun onLost(network: Network) {
            super.onLost(network)
            validNetworkConnections.remove(network)
            postValue(NetworkStatus.Unavailable)
        }

        override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
            super.onCapabilitiesChanged(network, networkCapabilities)

            if(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)){
                if(validNetworkConnections.isEmpty()){
                    postValue(NetworkStatus.Available)
                    validNetworkConnections.add(network)
                }
            }else{
                if(validNetworkConnections.isNotEmpty()){
                    postValue(NetworkStatus.Unavailable)
                    validNetworkConnections.remove(network)
                }
            }

        }
    }


    override fun onActive() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .build()

        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        when {
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> postValue(
                NetworkStatus.Available
            )
            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> postValue(
                NetworkStatus.Available
            )
            else -> postValue(NetworkStatus.Unavailable)
        }
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }


    override fun onInactive() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}
