package com.close.svea.refactoringsample.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils(private val context: Context) {

    fun isConnectedToNetwork(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}