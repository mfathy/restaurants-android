package me.mfathy.task.injection

import dagger.Module
import dagger.Provides
import me.mfathy.task.data.store.cache.CacheStore
import org.mockito.Mockito.mock

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Dagger module to provide remote dependencies.
 */
@Module
object TestCacheModule {


    @Provides
    @JvmStatic
    fun provideCacheCache(): CacheStore {
        return mock(CacheStore::class.java)
    }


}