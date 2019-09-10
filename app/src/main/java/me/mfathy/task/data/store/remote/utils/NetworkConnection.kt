package me.mfathy.task.data.store.remote.utils

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
interface NetworkConnection {
    /**
     * Check internet connectivity using the android ConnectivityManager
     * Returns true if there is a connection otherwise false.
     */
    fun hasInternetConnection(): Boolean
}