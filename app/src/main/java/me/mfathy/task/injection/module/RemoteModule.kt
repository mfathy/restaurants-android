package me.mfathy.task.injection.module

import android.content.Context
import com.google.gson.Gson
import me.mfathy.task.BuildConfig
import me.mfathy.task.data.store.remote.RemoteDataStore
import me.mfathy.task.data.store.remote.RemoteStore
import me.mfathy.task.data.store.remote.service.RemoteServiceApi
import me.mfathy.task.data.store.remote.utils.NetworkConnection
import me.mfathy.task.data.store.remote.utils.NetworkConnectionImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import me.mfathy.task.data.store.remote.service.RemoteServiceApiImpl

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Dagger module to provide remote dependencies.
 */
@Module
abstract class RemoteModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun providesNetworkConnection(context: Context): NetworkConnection =
            NetworkConnectionImpl(context)

        @Provides
        @JvmStatic
        fun providesRemoteService(context: Context): RemoteServiceApi {
            return RemoteServiceApiImpl(context, Gson())
        }
    }

    @Binds
    abstract fun bindRemoteStore(remote: RemoteDataStore): RemoteStore


}