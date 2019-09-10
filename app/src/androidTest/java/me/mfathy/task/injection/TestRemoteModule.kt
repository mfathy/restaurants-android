package me.mfathy.task.injection

import me.mfathy.task.data.store.remote.RemoteStore
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock

@Module
object TestRemoteModule {

    @Provides
    @JvmStatic
    fun provideRemoteDataStore(): RemoteStore {
        return mock(RemoteStore::class.java)
    }

}