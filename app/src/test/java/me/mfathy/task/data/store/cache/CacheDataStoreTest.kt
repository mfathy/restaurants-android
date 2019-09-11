package me.mfathy.task.data.store.cache

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import konveyor.base.randomBuild
import me.mfathy.task.base.BaseUnitTest
import me.mfathy.task.data.mapper.CachedRestaurantMapper
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.store.cache.db.RestaurantsDatabase
import me.mfathy.task.data.store.cache.models.CachedRestaurant
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@RunWith(RobolectricTestRunner::class)
class CacheDataStoreTest : BaseUnitTest() {
    private val mockMapper: CachedRestaurantMapper = mock(CachedRestaurantMapper::class.java)

    private val mapper = CachedRestaurantMapper()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application.applicationContext,
        RestaurantsDatabase::class.java
    )
        .allowMainThreadQueries()
        .build()

    override fun postSetup() {}

    private var cacheDataStore: CacheDataStore = CacheDataStore(
        database, mockMapper
    )

    @Test
    @Throws(Exception::class)
    fun testBookmark_Completes() {
        val entity = randomBuild(Restaurant::class.java)
        val cached = mapper.mapFromEntity(entity)
        entity.isFavorite = true

        stubMapperMapsFromEntity(cached)

        cacheDataStore.bookmark(entity)
            .test()
            .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun testUnBookmark_Completes() {
        val entity = randomBuild(Restaurant::class.java)
        val cached = mapper.mapFromEntity(entity)
        entity.isFavorite = false

        stubMapperMapsFromEntity(cached)

        cacheDataStore.unBookmark(entity)
            .test()
            .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun testGetFavorites_Completes() {
        val entity = randomBuild(Restaurant::class.java)
        val cached = mapper.mapFromEntity(entity)

        stubMapperMapsFromEntity(cached)

        cacheDataStore.bookmark(entity)

        cacheDataStore.getFavorites()
            .test()
            .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun testGetFavorites_ReturnsData() {
        val entity = randomBuild(Restaurant::class.java)
        val cached = mapper.mapFromEntity(entity)

        stubMapperMapsToEntity(entity)
        stubMapperMapsFromEntity(cached)

        cacheDataStore.bookmark(entity).test()

        cacheDataStore.getFavorites()
            .test()
            .assertValue(listOf(entity))

    }

    private fun stubMapperMapsToEntity(entity: Restaurant) {
        `when`(mockMapper.mapToEntity(me.mfathy.task.any()))
            .thenReturn(entity)
    }

    private fun stubMapperMapsFromEntity(cached: CachedRestaurant) {
        `when`(mockMapper.mapFromEntity(me.mfathy.task.any()))
            .thenReturn(cached)
    }
}
