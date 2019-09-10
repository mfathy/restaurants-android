package me.mfathy.task.interactors.favorites

import io.reactivex.Completable
import konveyor.base.randomBuild
import me.mfathy.task.ImmediateSchedulerRuleUnitTests
import me.mfathy.task.any
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
class UnBookmarkRestaurantTest {

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()

    @Mock
    lateinit var mockRepository: RestaurantsRepository

    private lateinit var unbookmarkRestaurant: UnBookmarkRestaurant

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        unbookmarkRestaurant = UnBookmarkRestaurant(mockRepository)
    }

    @Test
    fun testBookmarkRestaurant_Completes() {

        val entity = randomBuild(Restaurant::class.java)

        stubRepositoryBookmark()

        unbookmarkRestaurant
            .buildUseCaseCompletable(UnBookmarkRestaurant.Params(entity))
            .test()
            .assertComplete()
    }

    @Test
    fun testBookmarkRestaurant_CallRepository() {

        val entity = randomBuild(Restaurant::class.java)

        stubRepositoryBookmark()

        unbookmarkRestaurant.buildUseCaseCompletable(UnBookmarkRestaurant.Params(entity))
            .test()

        verify(mockRepository).unBookmarkRestaurant(any())
    }

    private fun stubRepositoryBookmark() {
        `when`(mockRepository.unBookmarkRestaurant(any())).thenReturn(Completable.complete())
    }
}