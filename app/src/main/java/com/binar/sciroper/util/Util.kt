package com.binar.sciroper.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat.getSystemService

fun checkNetworkAvailable(
    availableCallback: (isAvailable: Boolean) -> Unit,
) {
    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
//        .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
//        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
//        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            availableCallback(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            availableCallback(false)
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            availableCallback(false)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            availableCallback(false)
        }
    }
    val connectivityManager = getSystemService(App.context.get()!!, ConnectivityManager::class.java)
    connectivityManager?.requestNetwork(networkRequest, networkCallback)
}
