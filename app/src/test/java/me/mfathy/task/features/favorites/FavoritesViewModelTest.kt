package me.mfathy.task.features.favorites

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import me.mfathy.task.ImmediateSchedulerRuleUnitTests
import me.mfathy.task.any
import me.mfathy.task.argumentCaptor
import me.mfathy.task.capture
import me.mfathy.task.interactors.favorites.BookmarkRestaurant
import me.mfathy.task.interactors.favorites.UnBookmarkRestaurant
import me.mfathy.task.domain.model.Movie
import me.mfathy.task.factory.MoviesDataFactory
import me.mfathy.task.states.RestaurantResult
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@RunWith(JUnit4::class)
class FavoritesViewModelTest {

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    private val mockUnBookmarkMovie = mock(UnBookmarkRestaurant::class.java)
    private val mockBookmarkMovie = mock(BookmarkRestaurant::class.java)
    private val mockGetFavorites = mock(GetFavorites::class.java)


    private val viewModel: FavoritesViewModel =
        FavoritesViewModel(
            mockGetFavorites,
            mockBookmarkMovie,
            mockUnBookmarkMovie
        )

    @Captor
    val captor = argumentCaptor<DisposableSingleObserver<List<Movie>>>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testFetchFavoritesExecuteUseCase() {

        val observer = viewModel.GetFavoritesObserver()
        stubGetFavoriteUseCase(observer)

        viewModel.fetchFavorites()

        verify(mockGetFavorites).execute(any())
    }

    @Test
    fun testFetchFavoritesExecuteReturnsData() {

        val observer = viewModel.GetFavoritesObserver()
        stubGetFavoriteUseCase(observer)

        viewModel.fetchFavorites()
        verify(mockGetFavorites).execute(capture(captor))

        val movie = MoviesDataFactory.makeMovie()
        captor.value.onSuccess(listOf(movie))

        assertEqualMovie(movie, viewModel.getFavoritesLiveData().value)
    }

    @Test
    fun testFetchFavoritesExecuteReturnsSuccess() {

        val observer = viewModel.GetFavoritesObserver()
        stubGetFavoriteUseCase(observer)

        viewModel.fetchFavorites()
        verify(mockGetFavorites).execute(capture(captor))

        val movie = MoviesDataFactory.makeMovie()
        captor.value.onSuccess(listOf(movie))

        assert(viewModel.getFavoritesLiveData().value is RestaurantResult.OnSuccess)
    }

    @Test
    fun testFetchFavoritesExecuteReturnsFailure() {

        val observer = viewModel.GetFavoritesObserver()
        stubGetFavoriteUseCase(observer)

        viewModel.fetchFavorites()
        verify(mockGetFavorites).execute(capture(captor))

        captor.value.onError(Exception())

        assert(viewModel.getFavoritesLiveData().value is RestaurantResult.OnFailure)
    }

    private fun assertEqualMovie(expected: Movie, value: RestaurantResult?) {
        val actual = (value as RestaurantResult.OnSuccess).data.first()
        Assert.assertEquals(actual.id, expected.id)
        Assert.assertEquals(actual.rating, expected.rating, 0.5)
        Assert.assertEquals(actual.ratingCount, expected.ratingCount)
        Assert.assertEquals(actual.title, expected.title)
        Assert.assertEquals(actual.id, expected.id)
    }

    private fun stubGetFavoriteUseCase(observer: DisposableSingleObserver<List<Movie>>) {
        `when`(mockGetFavorites.execute(any())).thenReturn(observer)
    }

}