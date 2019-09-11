package me.mfathy.task.features.restaurants

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.reactivex.CompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import konveyor.base.randomBuild
import me.mfathy.task.ImmediateSchedulerRuleUnitTests
import me.mfathy.task.any
import me.mfathy.task.argumentCaptor
import me.mfathy.task.base.BaseUnitTest
import me.mfathy.task.capture
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.model.Sorting
import me.mfathy.task.data.model.SortingKeys
import me.mfathy.task.features.bookmark.BookmarkObserver
import me.mfathy.task.interactors.favorites.BookmarkRestaurant
import me.mfathy.task.interactors.favorites.UnBookmarkRestaurant
import me.mfathy.task.interactors.restaurants.GetRestaurants
import me.mfathy.task.states.BookmarkResult
import me.mfathy.task.states.RestaurantResult
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Captor
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class RestaurantsViewModelTest: BaseUnitTest() {
    private val mockRestaurants = mock(GetRestaurants::class.java)

    private val mockBookmark = mock(BookmarkRestaurant::class.java)
    private val mockUnBookmark = mock(UnBookmarkRestaurant::class.java)

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    private lateinit var viewModel: RestaurantsViewModel

    @Captor
    val restaurantCaptor = argumentCaptor<DisposableSingleObserver<List<Restaurant>>>()

    @Captor
    val bookmarkCaptor = argumentCaptor<CompletableObserver>()

    override fun postSetup() {
        viewModel = RestaurantsViewModel(mockRestaurants, mockBookmark, mockUnBookmark)
    }

    @Test
    fun testFetchRestaurants_ExecuteUseCase() {

        val observer = viewModel.GetRestaurantsObserver()
        stubGetRestaurantsUseCase(observer)

        viewModel.fetchRestaurants()

        verify(mockRestaurants).execute(any())
    }

    @Test
    fun testFetchRestaurants_ReturnsSuccess() {
        val entity = randomBuild(Restaurant::class.java)
        val restaurants = listOf(entity)

        val observer = viewModel.GetRestaurantsObserver()
        stubGetRestaurantsUseCase(observer)

        viewModel.fetchRestaurants()
        verify(mockRestaurants).execute(capture(restaurantCaptor))

        restaurantCaptor.value.onSuccess(restaurants)

        assert(viewModel.getRestaurantsLiveData().value is RestaurantResult.OnSuccess)
    }

    @Test
    fun testFetchRestaurants_ReturnsData() {
        val entity = randomBuild(Restaurant::class.java)
        val restaurants = listOf(entity)

        val observer = viewModel.GetRestaurantsObserver()
        stubGetRestaurantsUseCase(observer)

        viewModel.fetchRestaurants()
        verify(mockRestaurants).execute(capture(restaurantCaptor))

        restaurantCaptor.value.onSuccess(restaurants)

        assertEqualData(entity, viewModel.getRestaurantsLiveData().value)
    }

    @Test
    fun testFetchRestaurants_ReturnsFailure() {

        val observer = viewModel.GetRestaurantsObserver()
        stubGetRestaurantsUseCase(observer)

        viewModel.fetchRestaurants()
        verify(mockRestaurants).execute(capture(restaurantCaptor))

        restaurantCaptor.value.onError(Throwable())

        assert(viewModel.getRestaurantsLiveData().value is RestaurantResult.OnFailure)
    }

    @Test
    fun testBuildSortByOptionsComparator_SortData_ByBestMatch() {

        val sorting = randomBuild(Sorting::class.java)
        val restaurantOne = randomBuild(Restaurant::class.java)
            .copy(sortingValues = sorting.copy(bestMatch = 5), status = "open")
        val restaurantTwo = randomBuild(Restaurant::class.java)
            .copy(sortingValues = sorting.copy(bestMatch = 10), status = "open")
        val restaurantThree = randomBuild(Restaurant::class.java)
            .copy(sortingValues = sorting.copy(bestMatch = 3), status = "open")

        val list = mutableListOf(restaurantOne, restaurantTwo, restaurantThree)

        val bestMatchComparator = viewModel.buildSortByOptionsComparator(SortingKeys.BEST_MATCH)

        list.sortWith(bestMatchComparator)

        assertEquals(list.indexOf(restaurantTwo), 0)
        assertEquals(list.indexOf(restaurantOne), 1)
        assertEquals(list.indexOf(restaurantThree), 2)
    }

    @Test
    fun testBuildSortByOptionsComparator_SortData_ByOpenState() {

        val sorting = randomBuild(Sorting::class.java)
        val restaurantOne = randomBuild(Restaurant::class.java)
            .copy(sortingValues = sorting.copy(bestMatch = 5), status = "closed")
        val restaurantTwo = randomBuild(Restaurant::class.java)
            .copy(sortingValues = sorting.copy(bestMatch = 10), status = "order ahead")
        val restaurantThree = randomBuild(Restaurant::class.java)
            .copy(sortingValues = sorting.copy(bestMatch = 3), status = "open")

        val list = mutableListOf(restaurantOne, restaurantTwo, restaurantThree)

        val bestMatchComparator = viewModel.buildSortByOptionsComparator(SortingKeys.BEST_MATCH)

        list.sortWith(bestMatchComparator)

        assertEquals(list.indexOf(restaurantThree), 0)
        assertEquals(list.indexOf(restaurantTwo), 1)
        assertEquals(list.indexOf(restaurantOne), 2)
    }

    @Test
    fun testBuildSortByOptionsComparator_SortData_ByFavorite() {

        val sorting = randomBuild(Sorting::class.java)
        val restaurantOne = randomBuild(Restaurant::class.java)
            .copy(sortingValues = sorting.copy(bestMatch = 5), status = "closed", isFavorite = true)
        val restaurantTwo = randomBuild(Restaurant::class.java)
            .copy(sortingValues = sorting.copy(bestMatch = 10), status = "order ahead")
        val restaurantThree = randomBuild(Restaurant::class.java)
            .copy(sortingValues = sorting.copy(bestMatch = 3), status = "open")

        val list = mutableListOf(restaurantOne, restaurantTwo, restaurantThree)

        val bestMatchComparator = viewModel.buildSortByOptionsComparator(SortingKeys.BEST_MATCH)

        list.sortWith(bestMatchComparator)

        assertEquals(list.indexOf(restaurantOne), 0)
        assertEquals(list.indexOf(restaurantThree), 1)
        assertEquals(list.indexOf(restaurantTwo), 2)
    }

    @Test
    fun testSetBookmarkedRestaurant_Bookmarks() {
        val restaurant = randomBuild(Restaurant::class.java).copy(isFavorite = true)
        val observer = BookmarkObserver(viewModel, restaurant, 0)
        val actualResult = BookmarkResult.OnSuccess(Pair(0, restaurant))

        stubBookmarkRestaurantUseCase(restaurant, observer)

        viewModel.setBookmarkedRestaurant(restaurant, 0)

        verify(mockBookmark).execute(capture(bookmarkCaptor), any())

        bookmarkCaptor.value.onComplete()

        assertEquals(actualResult, viewModel.getBookmarkRestaurantLiveData().value)
    }

    @Test
    fun testSetBookmarkedRestaurant_Failure() {
        val restaurant = randomBuild(Restaurant::class.java).copy(isFavorite = true)
        val observer = BookmarkObserver(viewModel, restaurant, 0)
        val actualResult = BookmarkResult.OnFailure

        stubBookmarkRestaurantUseCase(restaurant, observer)

        viewModel.setBookmarkedRestaurant(restaurant, 0)

        verify(mockBookmark).execute(capture(bookmarkCaptor), any())

        bookmarkCaptor.value.onError(Throwable())

        assertEquals(actualResult, viewModel.getBookmarkRestaurantLiveData().value)
    }

    private fun assertEqualData(entity: Restaurant, value: RestaurantResult?) {
        val actual = (value as RestaurantResult.OnSuccess).data.first()
        assertEquals(entity.isFavorite, actual.isFavorite)
        assertEquals(entity.name, actual.name)
        assertEquals(entity.status, actual.status)
    }

    private fun stubBookmarkRestaurantUseCase(
        restaurant: Restaurant,
        observer: CompletableObserver
    ) {
        `when`(mockBookmark.execute(any(), any())).thenReturn(observer)
    }

    private fun stubGetRestaurantsUseCase(observer: DisposableSingleObserver<List<Restaurant>>) {
        `when`(mockRestaurants.execute(any())).thenReturn(observer)
    }

}