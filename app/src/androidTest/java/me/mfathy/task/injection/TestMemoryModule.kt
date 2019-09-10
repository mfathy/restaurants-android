package me.mfathy.task.injection

import com.bumptech.glide.load.engine.cache.MemoryCache
import dagger.Module
import dagger.Provides
import org.mockito.Mockito.mock

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Dagger module to provide remote dependencies.
 */
@Module
object TestMemoryModule {


    @Provides
    @JvmStatic
    fun provideMemoryCache(): MemoryCache {
        return mock(MemoryCache::class.java)
    }


}