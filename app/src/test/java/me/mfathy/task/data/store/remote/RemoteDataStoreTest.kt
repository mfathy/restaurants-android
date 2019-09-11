package me.mfathy.task.data.store.remote

import io.reactivex.Single
import me.mfathy.task.base.BaseUnitTest
import me.mfathy.task.data.store.remote.models.RestaurantsResponse
import me.mfathy.task.data.store.remote.service.RemoteServiceApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@RunWith(JUnit4::class)
class RemoteDataStoreTest: BaseUnitTest() {
    private val mockRemoteApi = mock(RemoteServiceApi::class.java)

    private val remoteStore = RemoteDataStore(mockRemoteApi)

    override fun postSetup() {}

    @Test
    fun testGetRestaurants_Completes() {

        val restaurantsResponse = RestaurantsResponse(listOf())

        `when`(mockRemoteApi.getRestaurants()).thenReturn(Single.just(restaurantsResponse))

        remoteStore.getRestaurants()
            .test()
            .assertComplete()
    }

    @Test
    fun testGetRestaurants_ReturnsData() {

        val restaurantsResponse = RestaurantsResponse(listOf())

        `when`(mockRemoteApi.getRestaurants()).thenReturn(Single.just(restaurantsResponse))

        remoteStore.getRestaurants()
            .test()
            .assertValue(restaurantsResponse)
    }

    @Test
    fun testGetRestaurants_Calls_Service() {

        val restaurantsResponse = RestaurantsResponse(listOf())

        `when`(mockRemoteApi.getRestaurants()).thenReturn(Single.just(restaurantsResponse))

        remoteStore.getRestaurants()
            .test()


        verify(mockRemoteApi).getRestaurants()
    }
}