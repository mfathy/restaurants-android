package me.mfathy.task.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import konveyor.base.randomBuild
import me.mfathy.task.any
import me.mfathy.task.data.mapper.RestaurantMapper
import me.mfathy.task.data.mapper.SortingValueMapper
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.base.BaseUnitTest
import me.mfathy.task.data.store.cache.CacheStore
import me.mfathy.task.data.store.remote.RemoteStore
import me.mfathy.task.data.store.remote.models.RestaurantItem
import me.mfathy.task.data.store.remote.models.RestaurantsResponse
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RestaurantsDataRepositoryTest : BaseUnitTest() {
    private var mockRemoteStore: RemoteStore = mock(RemoteStore::class.java)

    private var mockCacheStore: CacheStore = mock(CacheStore::class.java)
    private var mockMapper = mock(RestaurantMapper::class.java)

    private lateinit var repositoryUnderTest: RestaurantsDataRepository

    private val mapper = RestaurantMapper(SortingValueMapper())

    override fun postSetup() {
        repositoryUnderTest = RestaurantsDataRepository(mockRemoteStore, mockCacheStore, mockMapper)
    }

    @Test
    @Throws(Exception::class)
    fun testGetRestaurants_Completes() {

        val item = randomBuild(RestaurantItem::class.java)
        val response = RestaurantsResponse(listOf(item))
        val restaurant = mapper.mapToEntity(item)

        stubRemoteGetRestaurants(response)
        stubCacheGetFavorits(restaurant)

        repositoryUnderTest.getRestaurants()
            .test()
            .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun testGetRestaurants_ReturnData() {

        val item = randomBuild(RestaurantItem::class.java)
        val response = RestaurantsResponse(listOf(item))
        val restaurant = mapper.mapToEntity(item)

        stubRemoteGetRestaurants(response)
        stubCacheGetFavorits(restaurant)
        stubMapperMaptoEntity(restaurant)

        repositoryUnderTest.getRestaurants()
            .test()
            .assertValue(listOf(restaurant))
    }

    @Test
    @Throws(Exception::class)
    fun testGetRestaurants_CallsRepository() {

        val item = randomBuild(RestaurantItem::class.java)
        val response = RestaurantsResponse(listOf(item))
        val restaurant = mapper.mapToEntity(item)

        stubRemoteGetRestaurants(response)
        stubCacheGetFavorits(restaurant)

        repositoryUnderTest.getRestaurants()
            .test()

        verify(mockRemoteStore).getRestaurants()
        verify(mockCacheStore).getFavorites()
        verify(mockMapper).mapToEntity(any())
    }

    @Test
    @Throws(Exception::class)
    fun testBookmarkRestaurant_Completes() {
        val restaurant = randomBuild(Restaurant::class.java)

        stubCacheBookmark(restaurant)

        repositoryUnderTest.bookmarkRestaurant(restaurant)
            .test()
            .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun testUnBookmarkRestaurant_Completes() {
        val restaurant = randomBuild(Restaurant::class.java)

        stubCacheUnBookmark(restaurant)

        repositoryUnderTest.unBookmarkRestaurant(restaurant)
            .test()
            .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun testGetFavorites_Completes() {

        val restaurant = randomBuild(Restaurant::class.java)
        stubCacheGetFavorits(restaurant)

        repositoryUnderTest.getFavorites()
            .test()
            .assertComplete()
    }

    @Test
    @Throws(Exception::class)
    fun testGetFavorites_ReturnsData() {

        val restaurant = randomBuild(Restaurant::class.java)
        stubCacheGetFavorits(restaurant)

        repositoryUnderTest.getFavorites()
            .test()
            .assertValue(listOf(restaurant))
    }

    private fun stubMapperMaptoEntity(restaurant: Restaurant) {
        `when`(mockMapper.mapToEntity(any())).thenReturn(restaurant)
    }

    private fun stubCacheBookmark(restaurant: Restaurant) {
        `when`(mockCacheStore.bookmark(restaurant)).thenReturn(Completable.complete())
    }

    private fun stubCacheUnBookmark(restaurant: Restaurant) {
        `when`(mockCacheStore.unBookmark(restaurant)).thenReturn(Completable.complete())
    }

    private fun stubCacheGetFavorits(restaurant: Restaurant) {
        `when`(mockCacheStore.getFavorites()).thenReturn(Single.just(listOf(restaurant)))
    }

    private fun stubRemoteGetRestaurants(response: RestaurantsResponse) {
        `when`(mockRemoteStore.getRestaurants()).thenReturn(Single.just(response))
    }
}
