package me.mfathy.task.features.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import me.mfathy.task.argumentCaptor
import me.mfathy.task.capture
import me.mfathy.task.interactors.favorites.BookmarkRestaurant
import me.mfathy.task.interactors.favorites.UnBookmarkRestaurant
import me.mfathy.task.interactors.genres.GetGenres
import me.mfathy.task.interactors.restaurants.GetRestaurants
import me.mfathy.task.domain.model.Genre
import me.mfathy.task.factory.DataFactory
import me.mfathy.task.factory.MoviesDataFactory
import me.mfathy.task.states.DataException
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
import kotlin.test.assertEquals


/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Unit test for SearchViewModel
 */
@RunWith(JUnit4::class)
class SearchViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private val mockGetMovies = mock(GetRestaurants::class.java)
    private val mockGetGenre = mock(GetGenres::class.java)
    private val mockBookmarkMovie = mock(BookmarkRestaurant::class.java)
    private val mockUnBookmarkMovie = mock(UnBookmarkRestaurant::class.java)

    private val genres = DataFactory.makeGenres()

    private val viewModel = SearchViewModel(
        mockGetGenre,
        mockGetMovies,
        mockBookmarkMovie,
        mockUnBookmarkMovie
    )

    @Captor
    val genresCaptor = argumentCaptor<DisposableSingleObserver<List<Genre>>>()


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    /**
     * Search for unknown genre should return a NotFoundException.
     */
    @Test
    fun testSearchForTextNotExists() {
        viewModel.search(genres, "fff")

        val actualException = SearchResult.OnFailure(
            DataException.NotFoundException
        )

        Assert.assertEquals(viewModel.getSearchResultLiveData().value, actualException)
    }

    /**
     * Search for matching case genre should return success with genre name.
     */
    @Test
    fun testSearchForMatchCaseGenre() {
        viewModel.search(genres, "Crime")

        val actualResult = SearchResult.OnSuccess(
            Genre(
                name = "Crime",
                id = 0
            )
        )
        Assert.assertEquals(viewModel.getSearchResultLiveData().value, actualResult)
    }

    /**
     * Search for non matching case genre should return success with genre name.
     */
    @Test
    fun testSearchForNonMatchCaseGenre() {
        viewModel.search(genres, "crime")

        val actualResult = SearchResult.OnSuccess(
            Genre(
                name = "Crime",
                id = 0
            )
        )
        Assert.assertEquals(viewModel.getSearchResultLiveData().value, actualResult)
    }

    /**
     * Search for matching case of 2 or more genres should return success with the first genre name.
     */
    @Test
    fun testSearchForMatchCaseFirstGenreInMultipleWords() {
        viewModel.search(genres, "Crime Action Horror")

        val actualResult = SearchResult.OnSuccess(
            Genre(
                name = "Crime",
                id = 0
            )
        )
        Assert.assertEquals(viewModel.getSearchResultLiveData().value, actualResult)
    }

    /**
     * Search for non matching case of 2 or more genres should return success with the first genre name.
     */
    @Test
    fun testSearchForNonMatchCaseFirstGenreInMultipleWords() {
        viewModel.search(genres, "crime action horror")

        val actualResult = SearchResult.OnSuccess(
            Genre(
                name = "Crime",
                id = 0
            )
        )
        Assert.assertEquals(viewModel.getSearchResultLiveData().value, actualResult)
    }

    /**
     * Search for matching case of 2 words genre should return success with the genre name.
     */
    @Test
    fun testSearchForMatchCaseGenreWith2Words() {
        viewModel.search(genres, "Science Fiction")

        val actualResult = SearchResult.OnSuccess(
            Genre(
                name = "Science Fiction",
                id = 0
            )
        )
        Assert.assertEquals(viewModel.getSearchResultLiveData().value, actualResult)
    }

    /**
     * Search for non matching case of 2 words genre should return success with the genre name.
     */
    @Test
    fun testSearchForNonMatchCaseGenreWith2Words() {
        viewModel.search(genres, "science fiction")

        val actualResult = SearchResult.OnSuccess(
            Genre(
                name = "Science Fiction",
                id = 0
            )
        )
        Assert.assertEquals(viewModel.getSearchResultLiveData().value, actualResult)
    }

    /**
     * Search for matching case of 2 words genre + more genres should return success with the first 2 words genre name.
     */
    @Test
    fun testSearchForMatchCaseGenreWith2WordsInMultipleWords() {
        viewModel.search(genres, "Science Fiction Action")

        val actualResult = SearchResult.OnSuccess(
            Genre(
                "Science Fiction",
                0
            )
        )
        Assert.assertEquals(viewModel.getSearchResultLiveData().value, actualResult)
    }

    /**
     * Search for non matching case of 2 words genre + more genres should return success with the first 2 words genre name.
     */
    @Test
    fun testSearchForNonMatchCaseGenreWith2WordsInMultipleWords() {
        viewModel.search(genres, "science fiction action")

        val actualResult = SearchResult.OnSuccess(
            Genre(
                name = "Science Fiction",
                id = 0
            )
        )
        Assert.assertEquals(viewModel.getSearchResultLiveData().value, actualResult)
    }

    /**
     * Unit test to calculate median for odd list size.
     */
    @Test
    fun testCalculateMedianForOddListReturnValue() {
        val list = listOf(0.0, 1.0, 4.0, 6.0, 8.0)
        val median = viewModel.calculateMedian(list)

        assertEquals(median, 4.0)
    }

    /**
     * Unit test to calculate median for even list size.
     */
    @Test
    fun testCalculateMedianForEvenListReturnValue() {
        val list = listOf(0.0, 1.0, 4.0, 6.0, 8.0, 10.0)
        val median = viewModel.calculateMedian(list)

        assertEquals(median, 6.0)
    }

    @Test
    fun testGetGenresExecuteUseCase() {
        val observer = viewModel.GetGenresObserver("Action")

        stubGetGenresUseCase(observer)

        viewModel.searchGenres("Action")

        verify(mockGetGenre, times(1)).execute(me.mfathy.task.any())
    }

    @Test
    fun testGetGenresReturnsSuccess() {
        val genre = MoviesDataFactory.makeGenre()
        val observer = viewModel.GetGenresObserver(genre.name)

        stubGetGenresUseCase(observer)

        viewModel.searchGenres(genre.name)
        verify(mockGetGenre).execute(capture(genresCaptor))


        genresCaptor.value.onSuccess(listOf(genre))

        assert(viewModel.getSearchResultLiveData().value is SearchResult.OnSuccess)
    }

    @Test
    fun testGetGenresReturnsFailure() {
        val genre = MoviesDataFactory.makeGenre()
        val observer = viewModel.GetGenresObserver(genre.name)

        stubGetGenresUseCase(observer)

        viewModel.searchGenres(genre.name)
        verify(mockGetGenre).execute(capture(genresCaptor))


        genresCaptor.value.onError(Exception())

        assert(viewModel.getSearchResultLiveData().value is SearchResult.OnFailure)
    }

    @Test
    fun testGetGenresReturnsData() {
        val genre = MoviesDataFactory.makeGenre()
        val observer = viewModel.GetGenresObserver(genre.name)

        stubGetGenresUseCase(observer)

        viewModel.searchGenres(genre.name)
        verify(mockGetGenre).execute(capture(genresCaptor))


        genresCaptor.value.onSuccess(listOf(genre))

        assertEqualGenres(genre, viewModel.getSearchResultLiveData().value)
    }

    private fun assertEqualGenres(genre: Genre, value: SearchResult?) {
        val expectedGenre = (value as SearchResult.OnSuccess).data

        assertEquals(genre.name, expectedGenre.name)
        assertEquals(genre.id, expectedGenre.id)
    }

    private fun stubGetGenresUseCase(observer: DisposableSingleObserver<List<Genre>>) {
        `when`(mockGetGenre.execute(me.mfathy.task.any())).thenReturn(observer)
    }
}