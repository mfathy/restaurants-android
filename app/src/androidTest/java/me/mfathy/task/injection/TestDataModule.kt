package me.mfathy.task.injection

import dagger.Module
import dagger.Provides
import me.mfathy.task.data.repository.RestaurantsRepository
import org.mockito.Mockito.mock
import javax.inject.Singleton

@Module
object TestDataModule {

    @Provides
    @JvmStatic
    @Singleton
    fun provideDataRepository(): RestaurantsRepository {
        return mock(RestaurantsRepository::class.java)
    }

}