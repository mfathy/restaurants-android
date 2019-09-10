package me.mfathy.task.data.store.remote

import me.mfathy.task.data.model.GenreEntity
import me.mfathy.task.data.model.MovieEntity
import me.mfathy.task.data.model.MovieResponse
import me.mfathy.task.data.model.NetworkException
import me.mfathy.task.data.store.remote.service.RemoteServiceApi
import me.mfathy.task.data.store.remote.utils.NetworkConnection
import me.mfathy.task.factory.MoviesDataFactory
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
class RemoteStoreTest {

    private val mockNetworkConnection = mock(NetworkConnection::class.java)
    private val mockServiceApi = mock(RemoteServiceApi::class.java)

    private val remote = RemoteDataStore(
        mockNetworkConnection,
        mockServiceApi
    )

    @Test
    fun testGetGenreEntitiesThrowNetworkException() {
        stubNetworkConnections(false)

        remote.getGenreEntities()
            .test()
            .assertError(NetworkException::class.java)
    }

    @Test
    fun testGetGenreEntitiesCompletes() {
        val entity = MoviesDataFactory.makeGenreEntity()

        stubNetworkConnections(true)
        stubRemoteServiceGetGenreEntities(entity)

        remote.getGenreEntities()
            .test()
            .assertComplete()
    }

    @Test
    fun testGetGenreEntitiesReturnData() {
        val entity = MoviesDataFactory.makeGenreEntity()

        stubNetworkConnections(true)
        stubRemoteServiceGetGenreEntities(entity)

        remote.getGenreEntities()
            .test()
            .assertValue(listOf(entity))
    }

    @Test
    fun testGetGenreEntitiesCallService() {
        val entity = MoviesDataFactory.makeGenreEntity()

        stubNetworkConnections(true)
        stubRemoteServiceGetGenreEntities(entity)

        remote.getGenreEntities()
            .test()

        verify(mockNetworkConnection).hasInternetConnection()
        verify(mockServiceApi).getGenres()
    }

    @Test
    fun testGetMovieEntitiesThrowNetworkException() {
        stubNetworkConnections(false)

        remote.getMovieEntities("Drama", 1, 10)
            .test()
            .assertError(NetworkException::class.java)
    }

    @Test
    fun testGetMovieEntitiesCompletes() {
        val entity = MoviesDataFactory.makeMovieEntity()

        stubNetworkConnections(true)
        stubRemoteServiceGetMoviesEntities(entity)

        remote.getMovieEntities("Drama", 1, 10)
            .test()
            .assertComplete()
    }

    @Test
    fun testGetMovieEntitiesReturnsData() {
        val entity = MoviesDataFactory.makeMovieEntity()

        stubNetworkConnections(true)
        stubRemoteServiceGetMoviesEntities(entity)

        remote.getMovieEntities("Drama", 1, 10)
            .test()
            .assertValue(MovieResponse(listOf(entity)))
    }

    @Test
    fun testGetMovieEntitiesCallService() {
        val entity = MoviesDataFactory.makeMovieEntity()

        stubNetworkConnections(true)
        stubRemoteServiceGetMoviesEntities(entity)

        remote.getMovieEntities("Drama", 1, 10)
            .test()

        verify(mockNetworkConnection).hasInternetConnection()
        verify(mockServiceApi).getMovies(
            ArgumentMatchers.anyString(),
            ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()
        )
    }

    private fun stubRemoteServiceGetMoviesEntities(entity: MovieEntity) {
        `when`(
            mockServiceApi.getMovies(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt(), ArgumentMatchers.anyInt()
            )
        ).thenReturn(
            Flowable.just(
                MovieResponse(
                    listOf(entity)
                )
            )
        )
    }

    private fun stubRemoteServiceGetGenreEntities(entity: GenreEntity) {
        `when`(mockServiceApi.getGenres()).thenReturn(Single.just(listOf(entity)))
    }

    private fun stubNetworkConnections(network: Boolean) {
        `when`(mockNetworkConnection.hasInternetConnection()).thenReturn(network)
    }
}