package me.mfathy.task.injection

import dagger.Module
import dagger.Provides
import me.mfathy.task.data.store.remote.RemoteStore
import org.mockito.Mockito.mock

@Module
object TestRemoteModule {

    @Provides
    @JvmStatic
    fun provideRemoteDataStore(): RemoteStore {
        return mock(RemoteStore::class.java)
    }

}