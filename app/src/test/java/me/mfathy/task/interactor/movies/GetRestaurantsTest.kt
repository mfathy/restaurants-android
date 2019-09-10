package me.mfathy.task.interactor.movies

import me.mfathy.task.domain.model.Movie
import me.mfathy.task.data.repository.RestaurantsRepository
import me.mfathy.task.factory.MoviesDataFactory
import io.reactivex.Flowable
import me.mfathy.task.interactors.restaurants.GetRestaurants
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Unit test for GetRestaurants use case.
 */
@RunWith(MockitoJUnitRunner::class)
class GetRestaurantsTest {

    private lateinit var getRestaurants: GetRestaurants

    @Mock
    lateinit var mockRepository: RestaurantsRepository


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getRestaurants = GetRestaurants(mockRepository)
    }


    @Test
    fun testGetMoviesCompletes() {

        val expected = MoviesDataFactory.makeMovie()

        stubDataRepositoryGetMovies(expected)

        val params = MoviesDataFactory.makeGetMoviesParams()

        getRestaurants.execute(params)
            .test()
            .assertComplete()
    }

    @Test
    fun testGetMoviesCallsRepository() {
        val expected = MoviesDataFactory.makeMovie()

        stubDataRepositoryGetMovies(expected)

        val params = MoviesDataFactory.makeGetMoviesParams()

        getRestaurants.execute(params)
            .test()

        Mockito.verify(mockRepository, Mockito.times(1)).getMovies(anyString(), anyInt(), anyInt())
    }

    @Test
    fun testGetMoviesReturnsCorrectData() {
        val expected = MoviesDataFactory.makeMovie()

        stubDataRepositoryGetMovies(expected)

        val params = MoviesDataFactory.makeGetMoviesParams()

        getRestaurants.execute(params)
            .test()
            .assertValue(listOf(expected))
    }

    private fun stubDataRepositoryGetMovies(expected: Movie) {
        `when`(mockRepository.getMovies(anyString(), anyInt(), anyInt()))
            .thenReturn(Flowable.just(listOf(expected)))
    }
}