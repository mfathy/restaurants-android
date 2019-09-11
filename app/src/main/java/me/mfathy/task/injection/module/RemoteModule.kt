package me.mfathy.task.injection.module

import android.content.Context
import com.google.gson.Gson
import dagger.Binds
import dagger.Module
import dagger.Provides
import me.mfathy.task.data.store.remote.RemoteDataStore
import me.mfathy.task.data.store.remote.RemoteStore
import me.mfathy.task.data.store.remote.service.RemoteServiceApi
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
        fun providesRemoteService(context: Context): RemoteServiceApi {
            return RemoteServiceApiImpl(context, Gson())
        }
    }

    @Binds
    abstract fun bindRemoteStore(remote: RemoteDataStore): RemoteStore


}