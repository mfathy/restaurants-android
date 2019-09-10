package me.mfathy.task.features.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import me.mfathy.task.any
import me.mfathy.task.argumentCaptor
import me.mfathy.task.capture
import me.mfathy.task.interactors.favorites.BookmarkRestaurant
import me.mfathy.task.interactors.favorites.UnBookmarkRestaurant
import me.mfathy.task.domain.model.Movie
import me.mfathy.task.factory.MoviesDataFactory
import me.mfathy.task.features.bookmark.BookmarkObserver
import me.mfathy.task.states.BookmarkResult
import io.reactivex.CompletableObserver
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import kotlin.test.assertEquals

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@RunWith(JUnit4::class)
class BaseViewModelTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val mockUnBookmarkMovie = mock(UnBookmarkRestaurant::class.java)
    private val mockBookMarkMovie = mock(BookmarkRestaurant::class.java)

    private val viewModel: BaseViewModel =
        BaseViewModel(mockBookMarkMovie, mockUnBookmarkMovie)

    @Captor
    val captor = argumentCaptor<CompletableObserver>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testBookmarkMovieSuccess() {
        val movie = MoviesDataFactory.makeMovie()

        val actualResult = BookmarkResult.OnSuccess(Pair(0, movie))

        stubBookmarkMovieUseCase(movie)
        viewModel.setBookmarkedRestaurant(movie, 0)

        Mockito.verify(mockBookMarkMovie).execute(
            capture(captor),
            any()
        )

        captor.value.onComplete()

        assertEquals(actualResult, viewModel.getBookmarkRestaurantLiveData().value)
    }

    @Test
    fun testBookmarkMovieFailure() {
        val movie = MoviesDataFactory.makeMovie()

        val actualResult = BookmarkResult.OnFailure

        stubBookmarkMovieUseCase(movie)
        viewModel.setBookmarkedRestaurant(movie, 0)

        Mockito.verify(mockBookMarkMovie).execute(
            capture(captor),
            any()
        )

        captor.value.onError(Exception())

        assertEquals(actualResult, viewModel.getBookmarkRestaurantLiveData().value)
    }

    @Test
    fun testBookmarkMovieExecuteUseCase() {
        val movie = MoviesDataFactory.makeMovie()
        stubBookmarkMovieUseCase(movie)
        viewModel.setBookmarkedRestaurant(movie, 0)

        Mockito.verify(mockBookMarkMovie).execute(any(), any())
    }

    @Test
    fun testUnBookmarkMovieSuccess() {
        val movie = MoviesDataFactory.makeMovie()

        val actualResult = BookmarkResult.OnSuccess(Pair(0, movie))

        stubUnBookmarkMovieUseCase(movie)
        viewModel.setUnBookmarkedRestaurant(movie, 0)

        Mockito.verify(mockUnBookmarkMovie).execute(
            capture(captor),
            any()
        )

        captor.value.onComplete()

        assertEquals(actualResult, viewModel.getBookmarkRestaurantLiveData().value)
    }

    @Test
    fun testUnBookmarkMovieFailure() {
        val movie = MoviesDataFactory.makeMovie()

        val actualResult = BookmarkResult.OnFailure

        stubUnBookmarkMovieUseCase(movie)
        viewModel.setUnBookmarkedRestaurant(movie, 0)

        Mockito.verify(mockUnBookmarkMovie).execute(
            capture(captor),
            any()
        )

        captor.value.onError(Exception())

        assertEquals(actualResult, viewModel.getBookmarkRestaurantLiveData().value)
    }

    @Test
    fun testUnBookmarkMovieExecuteUseCase() {
        val movie = MoviesDataFactory.makeMovie()
        stubUnBookmarkMovieUseCase(movie)
        viewModel.setUnBookmarkedRestaurant(movie, 0)

        Mockito.verify(mockUnBookmarkMovie).execute(any(), any())
    }

    private fun stubBookmarkMovieUseCase(movie: Movie) {
        `when`(mockBookMarkMovie.execute(any(), any())).thenReturn(
            BookmarkObserver(
                viewModel,
                movie,
                0,
                true
            )
        )
    }

    private fun stubUnBookmarkMovieUseCase(movie: Movie) {
        `when`(mockUnBookmarkMovie.execute(any(), any())).thenReturn(
            BookmarkObserver(
                viewModel,
                movie,
                0,
                false
            )
        )
    }
}