package me.mfathy.task.data.store.remote.utils

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.Mockito

/**
 * Created by Mohammed Fathy
 * dev.mfathy@gmail.com
 *
 * Unit test for NetworkConnectionImpl helper class.
 */
class NetworkConnectionTest {

    private val context = Mockito.mock<Context>(Context::class.java)
    private val connManager = Mockito.mock(ConnectivityManager::class.java)
    private val networkInfo = Mockito.mock(NetworkInfo::class.java)
    private val packageManager = Mockito.mock(PackageManager::class.java)
    private val utils = NetworkConnectionImpl(context)

    @Test
    fun testHasInternetConnectionReturnsTrue() {
        stubHasConnection()
        assertTrue(utils.hasInternetConnection())
    }

    private fun stubHasConnection() {
        Mockito.`when`(context.packageManager).thenReturn(packageManager)
        Mockito.`when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connManager)
        Mockito.`when`(connManager.activeNetworkInfo).thenReturn(networkInfo)
        Mockito.`when`(networkInfo.isConnected).thenReturn(true)
    }
}