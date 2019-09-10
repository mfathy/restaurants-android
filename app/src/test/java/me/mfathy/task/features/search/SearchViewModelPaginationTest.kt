package me.mfathy.task.features.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import me.mfathy.task.ImmediateSchedulerRuleUnitTests
import me.mfathy.task.data.model.NetworkException
import me.mfathy.task.interactors.favorites.BookmarkRestaurant
import me.mfathy.task.interactors.favorites.UnBookmarkRestaurant
import me.mfathy.task.interactors.genres.GetGenres
import me.mfathy.task.interactors.restaurants.GetRestaurants
import me.mfathy.task.domain.model.Movie
import me.mfathy.task.factory.MoviesDataFactory
import me.mfathy.task.states.RestaurantResult
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.net.ConnectException
import kotlin.test.assertEquals


/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Unit test for SearchViewModel
 */
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelPaginationTest {

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()


    private val mockGetMovies = mock(GetRestaurants::class.java)
    private val mockGetGenre = mock(GetGenres::class.java)
    private val mockBookmarkMovie = mock(BookmarkRestaurant::class.java)
    private val mockUnBookmarkMovie = mock(UnBookmarkRestaurant::class.java)


    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = SearchViewModel(
            mockGetGenre,
            mockGetMovies,
            mockBookmarkMovie,
            mockUnBookmarkMovie
        )
    }

    @Test
    fun testGetMoviesPaginationExecuteUseCase() {
        val movie = MoviesDataFactory.makeMovie()

        stubGetMoviesUseCaseSuccess(movie)

        viewModel.startPagination()
        viewModel.getPaginator().onNext(Pair(0, "Action"))

        verify(mockGetMovies, times(1)).execute(me.mfathy.task.any())
    }

    @Test
    fun testGetMoviesPaginationReturnSuccess() {
        val movie = MoviesDataFactory.makeMovie()

        stubGetMoviesUseCaseSuccess(movie)

        viewModel.subscribeToPagination(viewModel.GetRestaurantsObserver())
        viewModel.getPaginator().onNext(Pair(0, "Action"))

        assert(viewModel.getRestaurantsLiveData().value is RestaurantResult.OnSuccess)
    }

    @Test
    fun testGetMoviesPaginationReturnGenericFailure() {

        stubGetMoviesUseCaseFailure(Exception())

        viewModel.subscribeToPagination(viewModel.GetRestaurantsObserver())
        viewModel.getPaginator().onNext(Pair(0, "ffff"))

        assert(viewModel.getRestaurantsLiveData().value is RestaurantResult.OnFailure)
    }

    @Test
    fun testGetMoviesPaginationReturnNoNetworkFailure() {

        stubGetMoviesUseCaseFailure(NetworkException())

        viewModel.subscribeToPagination(viewModel.GetRestaurantsObserver())
        viewModel.getPaginator().onNext(Pair(0, "ffff"))

        assert(viewModel.getRestaurantsLiveData().value is RestaurantResult.OnFailure)
    }

    @Test
    fun testGetMoviesPaginationReturnNoConnectionToServerFailure() {

        stubGetMoviesUseCaseFailure(ConnectException())

        viewModel.subscribeToPagination(viewModel.GetRestaurantsObserver())
        viewModel.getPaginator().onNext(Pair(0, "ffff"))

        assert(viewModel.getRestaurantsLiveData().value is RestaurantResult.OnFailure)
    }

    @Test
    fun testGetMoviesPaginationReturnData() {
        val movie = MoviesDataFactory.makeMovie()

        stubGetMoviesUseCaseSuccess(movie)

        viewModel.subscribeToPagination(viewModel.GetRestaurantsObserver())
        viewModel.getPaginator().onNext(Pair(0, "Action"))

        assertEquals(viewModel.getRestaurantsLiveData().value, RestaurantResult.OnSuccess(listOf(movie)))
    }

    private fun stubGetMoviesUseCaseSuccess(movie: Movie) {
        `when`(mockGetMovies.execute(me.mfathy.task.any())).thenReturn(
            Flowable.just(listOf(movie))
        )
    }

    private fun stubGetMoviesUseCaseFailure(exception: Exception) {
        `when`(mockGetMovies.execute(me.mfathy.task.any())).thenReturn(
            Flowable.error { exception }
        )
    }
}