package com.vandoc.iptv.util

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.channelFlow
import timber.log.Timber
import java.net.InetAddress
import java.net.UnknownHostException

/**
 * @author Achmad Ichsan
 * @version ConnectionFlow, v 0.1 11/07/22 13.49 by Achmad Ichsan
 */
class ConnectionFlow(context: Context) {

    private val connectivityManager =
        context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    private val connectionFlow = channelFlow {
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onLost(network: Network) {
                trySend(false)
            }

            override fun onAvailable(network: Network) {
                trySend(true)
            }

            override fun onUnavailable() {
                trySend(false)
            }
        }
        val request = NetworkRequest.Builder().addCapability(NET_CAPABILITY_INTERNET).build()

        connectivityManager.registerNetworkCallback(request, networkCallback)

        val requestConnectionJob = CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                try {
                    val result = !InetAddress.getByName("www.google.com").equals("")
                    trySend(result)
                } catch (e: UnknownHostException) {
                    trySend(false)
                    Timber.e(e)
                }

                delay(5000)
            }
        }

        awaitClose {
            connectivityManager.unregisterNetworkCallback(networkCallback)
            requestConnectionJob.cancel()
        }
    }

    private val _connectionState = MutableSharedFlow<Boolean>(1, 16, BufferOverflow.DROP_OLDEST)
    val connectionState get() = _connectionState.asSharedFlow()

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _connectionState.run {
                connectionFlow.collect(::emit)
            }
        }
    }
}
