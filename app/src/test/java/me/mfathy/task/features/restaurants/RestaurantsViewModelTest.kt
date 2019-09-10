package me.mfathy.task.features.restaurants

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.observers.DisposableSingleObserver
import konveyor.base.randomBuild
import me.mfathy.task.ImmediateSchedulerRuleUnitTests
import me.mfathy.task.any
import me.mfathy.task.argumentCaptor
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.interactors.favorites.BookmarkRestaurant
import me.mfathy.task.interactors.favorites.UnBookmarkRestaurant
import me.mfathy.task.interactors.restaurants.GetRestaurants
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Captor
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class RestaurantsViewModelTest {

    private val mockRestaurants = mock(GetRestaurants::class.java)
    private val mockBookmark = mock(BookmarkRestaurant::class.java)
    private val mockUnBookmark = mock(UnBookmarkRestaurant::class.java)

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    private lateinit var viewModel: RestaurantsViewModel

    @Captor
    val captor = argumentCaptor<DisposableSingleObserver<List<Restaurant>>>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = RestaurantsViewModel(mockRestaurants, mockBookmark, mockUnBookmark)
    }

    @Test
    fun testFetchRestaurants_ExecuteUseCase() {
        val entity = randomBuild(Restaurant::class.java)
        val restaurants = listOf(entity)

        val observer = viewModel.GetRestaurantsObserver()
        stubGetRestaurantsUseCase(observer)

        viewModel.fetchRestaurants()

        verify(mockRestaurants).execute(any())
    }

    private fun stubGetRestaurantsUseCase(observer: DisposableSingleObserver<List<Restaurant>>) {
        `when`(mockRestaurants.execute(any())).thenReturn(observer)
    }

}