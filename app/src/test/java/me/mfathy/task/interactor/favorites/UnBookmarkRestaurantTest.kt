package me.mfathy.task.interactor.favorites

import me.mfathy.task.ImmediateSchedulerRuleUnitTests
import me.mfathy.task.any
import me.mfathy.task.data.repository.RestaurantsRepository
import me.mfathy.task.factory.MoviesDataFactory
import io.reactivex.Completable
import me.mfathy.task.interactors.favorites.UnBookmarkRestaurant
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

    private lateinit var unBookmarkMovie: UnBookmarkRestaurant

    @Mock
    lateinit var mockRepository: RestaurantsRepository


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        unBookmarkMovie = UnBookmarkRestaurant(mockRepository)
    }

    @Test
    fun testUnBookmarkMovieCompletes() {
        val movie = MoviesDataFactory.makeMovie()
        val params = UnBookmarkRestaurant.Params(movie)

        stubRepositoryBookmarkMovie()

        unBookmarkMovie.buildUseCaseCompletable(params)
            .test()
            .assertComplete()
    }

    @Test
    fun testBookmarkMovieCallRepository() {
        val movie = MoviesDataFactory.makeMovie()
        val params = UnBookmarkRestaurant.Params(movie)

        stubRepositoryBookmarkMovie()

        unBookmarkMovie.buildUseCaseCompletable(params)
            .test()

        verify(mockRepository).unBookmarkMovie(any())
    }

    private fun stubRepositoryBookmarkMovie() {
        `when`(mockRepository.unBookmarkMovie(any())).thenReturn(Completable.complete())
    }
}