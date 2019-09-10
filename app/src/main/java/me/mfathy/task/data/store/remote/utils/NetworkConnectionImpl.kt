package me.mfathy.task.data.store.remote.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import javax.inject.Inject

/**
 * Created by Mohammed Fathy
 * dev.mfathy@gmail.com
 *
 * Helper class to check internet connectivity
 */
open class NetworkConnectionImpl @Inject constructor(private val context: Context) :
    NetworkConnection {

    /**
     * Check internet connectivity using the android ConnectivityManager
     * Returns true if there is a connection otherwise false.
     */
    override fun hasInternetConnection(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            if (capabilities != null) capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || capabilities.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            )
            else
                false
        } else {
            val activeNetwork = cm.activeNetworkInfo
            (activeNetwork != null && activeNetwork.isConnected)
        }
    }
}