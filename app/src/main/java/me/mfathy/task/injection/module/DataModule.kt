package me.mfathy.task.injection.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import me.mfathy.task.data.mapper.CachedRestaurantMapper
import me.mfathy.task.data.mapper.RestaurantMapper
import me.mfathy.task.data.mapper.SortingValueMapper
import me.mfathy.task.data.repository.RestaurantsDataRepository
import me.mfathy.task.data.repository.RestaurantsRepository

/**
 * Dagger module to provide data repository dependencies.
 */
@Module
abstract class DataModule {

    @Module
    companion object {

        @Provides
        @JvmStatic
        fun providesRestaurantMapper(): RestaurantMapper =
            RestaurantMapper(SortingValueMapper())

        @Provides
        @JvmStatic
        fun providesSortingValueMapper(): SortingValueMapper =
            SortingValueMapper()

        @Provides
        @JvmStatic
        fun providesCachedRestaurantMapper(): CachedRestaurantMapper =
            CachedRestaurantMapper()

    }

    @Binds
    abstract fun bindDataRepository(dataRepository: RestaurantsDataRepository): RestaurantsRepository
}