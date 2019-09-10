package me.mfathy.task.interactor.favorites

import me.mfathy.task.ImmediateSchedulerRuleUnitTests
import me.mfathy.task.domain.model.Movie
import me.mfathy.task.data.repository.RestaurantsRepository
import me.mfathy.task.factory.MoviesDataFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class GetFavoritesTest {
    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()

    private lateinit var getFavorites: GetFavorites

    @Mock
    lateinit var mockRepository: RestaurantsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getFavorites = GetFavorites(mockRepository)
    }

    @Test
    fun testGetFavoritesCompletes() {

        val movie = MoviesDataFactory.makeMovie()
        stubDataRepositoryGetFavorites(movie)

        getFavorites.buildUseCaseSingle()
            .test()
            .assertComplete()

    }

    @Test
    fun testGetFavoritesCallRepository() {

        val movie = MoviesDataFactory.makeMovie()
        stubDataRepositoryGetFavorites(movie)

        getFavorites.buildUseCaseSingle()
            .test()


        Mockito.verify(mockRepository).getFavorites()
    }

    @Test
    fun testGetFavoritesReturnData() {

        val movie = MoviesDataFactory.makeMovie()
        stubDataRepositoryGetFavorites(movie)

        getFavorites.buildUseCaseSingle()
            .test()
            .assertValue(listOf(movie))
    }

    private fun stubDataRepositoryGetFavorites(movie: Movie) {
        Mockito.`when`(mockRepository.getFavorites()).thenReturn(Single.just(listOf(movie)))
    }
}