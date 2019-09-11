package me.mfathy.task.interactors.restaurants

import io.reactivex.Single
import konveyor.base.randomBuild
import me.mfathy.task.ImmediateSchedulerRuleUnitTests
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.repository.RestaurantsRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class GetRestaurantsTest {

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()

    private lateinit var getRestaurant: GetRestaurants

    @Mock
    lateinit var mockRepository: RestaurantsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getRestaurant = GetRestaurants(mockRepository)
    }

    @Test
    @Throws(Exception::class)
    fun testGetRestaurants_Completes() {

        val entity = randomBuild(Restaurant::class.java)

        stubRepositoryGetRestaurants(entity)

        getRestaurant
            .buildUseCaseSingle()
            .test()
            .assertComplete()

    }

    @Test
    @Throws(Exception::class)
    fun testGetRestaurants_ReturnsData() {

        val entity = randomBuild(Restaurant::class.java)

        stubRepositoryGetRestaurants(entity)

        getRestaurant
            .buildUseCaseSingle()
            .test()
            .assertValue(listOf(entity))

    }

    @Test
    @Throws(Exception::class)
    fun testGetRestaurants_Calls_Repository() {

        val entity = randomBuild(Restaurant::class.java)

        stubRepositoryGetRestaurants(entity)

        getRestaurant
            .buildUseCaseSingle()
            .test()

        verify(mockRepository).getRestaurants()

    }

    private fun stubRepositoryGetRestaurants(entity: Restaurant) {
        `when`(mockRepository.getRestaurants()).thenReturn(
            Single.just(listOf(entity))
        )
    }
}