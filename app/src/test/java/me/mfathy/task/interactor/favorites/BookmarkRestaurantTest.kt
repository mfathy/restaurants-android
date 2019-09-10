package me.mfathy.task.interactor.favorites

import me.mfathy.task.ImmediateSchedulerRuleUnitTests
import me.mfathy.task.any
import me.mfathy.task.data.repository.RestaurantsRepository
import me.mfathy.task.factory.MoviesDataFactory
import io.reactivex.Completable
import me.mfathy.task.interactors.favorites.BookmarkRestaurant
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
class BookmarkRestaurantTest {

    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()

    private lateinit var bookmarkRestaurant: BookmarkRestaurant

    @Mock
    lateinit var mockRepository: RestaurantsRepository


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        bookmarkRestaurant = BookmarkRestaurant(mockRepository)
    }

    @Test
    fun testBookmarkMovieCompletes() {
        val movie = MoviesDataFactory.makeMovie()
        val params = BookmarkRestaurant.Params(movie)

        stubRepositoryBookmarkMovie()

        bookmarkRestaurant.buildUseCaseCompletable(params)
            .test()
            .assertComplete()
    }

    @Test
    fun testBookmarkMovieCallRepository() {
        val movie = MoviesDataFactory.makeMovie()
        val params = BookmarkRestaurant.Params(movie)

        stubRepositoryBookmarkMovie()

        bookmarkRestaurant.buildUseCaseCompletable(params)
            .test()

        verify(mockRepository).bookmarkMovie(any())
    }

    private fun stubRepositoryBookmarkMovie() {
        `when`(mockRepository.bookmarkMovie(any())).thenReturn(Completable.complete())
    }
}