package me.mfathy.task.injection.module

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import me.mfathy.task.data.store.cache.CacheDataStore
import me.mfathy.task.data.store.cache.CacheStore
import me.mfathy.task.data.store.cache.db.RestaurantsDatabase

/**
 * Created by Mohammed Fathy
 * dev.mfathy@gmail.com
 *
 * Dagger module to provide cache dependencies.
 */
@Module
abstract class CacheModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesDatabase(application: Application): RestaurantsDatabase {
            return RestaurantsDatabase.getInstance(application)
        }
    }


    @Binds
    abstract fun bindCacheStore(cache: CacheDataStore): CacheStore
}