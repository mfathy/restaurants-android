package me.mfathy.task.injection.module

import me.mfathy.task.data.store.memory.MemoryCache
import me.mfathy.task.data.store.memory.MemoryDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Dagger module to provide remote dependencies.
 */
@Module
abstract class MemoryModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        @Singleton
        fun providesMemoryCache(
        ): MemoryDataStore {
            return MemoryDataStore()
        }
    }

    @Binds
    abstract fun bindRemoteStore(remote: MemoryDataStore): MemoryCache

}