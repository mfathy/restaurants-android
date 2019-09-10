package me.mfathy.task.interactor.genres

import me.mfathy.task.ImmediateSchedulerRuleUnitTests
import me.mfathy.task.domain.model.Genre
import me.mfathy.task.data.repository.RestaurantsRepository
import me.mfathy.task.factory.MoviesDataFactory
import io.reactivex.Single
import me.mfathy.task.interactors.genres.GetGenres
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Unit test for GetGenres use case.
 */
@RunWith(MockitoJUnitRunner::class)
class GetGenresTest {


    @JvmField
    @Rule
    val immediateSchedulerRule = ImmediateSchedulerRuleUnitTests()

    private lateinit var getGenres: GetGenres

    @Mock
    lateinit var mockRepository: RestaurantsRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getGenres = GetGenres(mockRepository)
    }


    @Test
    fun testGetGenresCompletes() {

        val expected = MoviesDataFactory.makeGenre()

        stubDataRepositoryGetGenres(expected)

        getGenres.buildUseCaseSingle()
            .test()
            .assertComplete()

    }

    @Test
    fun testGetGenresCallsRepository() {
        val expected = MoviesDataFactory.makeGenre()

        stubDataRepositoryGetGenres(expected)

        getGenres.buildUseCaseSingle()
            .test()

        verify(mockRepository, times(1)).getGenres()
    }

    @Test
    fun testGetGenresReturnsCorrectData() {
        val expected = MoviesDataFactory.makeGenre()

        stubDataRepositoryGetGenres(expected)

        getGenres.buildUseCaseSingle()
            .test()
            .assertValue(listOf(expected))
    }

    private fun stubDataRepositoryGetGenres(expected: Genre) {
        `when`(mockRepository.getGenres()).thenReturn(Single.just(listOf(expected)))
    }
}
