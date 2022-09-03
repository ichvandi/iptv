package com.vandoc.iptv.util

import android.app.Activity
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

/**
 * @author Achmad Ichsan
 * @version ConnectionFlow, v 0.2 03/09/22 21.00 by Ichvandi
 */
fun Context.monitorNetwork(): Flow<Boolean>? = findActivity()?.monitorNetwork()

fun Activity.monitorNetwork(): Flow<Boolean> = callbackFlow {
    val connectivityManager: ConnectivityManager =
        applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    val networkRequest = NetworkRequest.Builder()
        .addCapability(NET_CAPABILITY_INTERNET)
        .build()

    val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            val hasInternetCapability = networkCapabilities.hasCapability(
                NET_CAPABILITY_INTERNET
            )

            if (!hasInternetCapability) {
                trySend(false)
                return
            }

            val hasInternet = ping(network.socketFactory)
            if (!hasInternet) {
                trySend(false)
                return
            }

            trySend(true)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            trySend(false)
        }
    }

    connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

    awaitClose { connectivityManager.unregisterNetworkCallback(networkCallback) }
}

private fun ping(socketFactory: SocketFactory): Boolean {
    return try {
        val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
        socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
        socket.close()
        true
    } catch (e: IOException) {
        false
    }
}
