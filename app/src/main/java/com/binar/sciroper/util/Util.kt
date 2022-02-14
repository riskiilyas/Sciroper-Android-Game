package com.binar.sciroper.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat.getSystemService

fun checkNetworkAvailable(
    context: Context,
    availableCallback: (isAvailable: Boolean) -> Unit,
) {
    val networkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .build()

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            availableCallback(true)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            availableCallback(false)
        }
    }
    val connectivityManager = getSystemService(context, ConnectivityManager::class.java)
    connectivityManager?.requestNetwork(networkRequest, networkCallback)
}
