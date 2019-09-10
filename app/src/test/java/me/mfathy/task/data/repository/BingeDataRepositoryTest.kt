package me.mfathy.task.data.repository

import me.mfathy.task.data.model.GenreEntity
import me.mfathy.task.data.model.MovieEntity
import me.mfathy.task.data.model.MovieResponse
import me.mfathy.task.data.store.memory.MemoryCache
import me.mfathy.task.data.store.remote.RemoteStore
import me.mfathy.task.domain.model.Genre
import me.mfathy.task.domain.model.Movie
import me.mfathy.task.factory.MoviesDataFactory
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@RunWith(MockitoJUnitRunner::class)
class BingeDataRepositoryTest {

    private val mockMovieMapper = mock(MovieDataMapper::class.java)
    private val mockGenreMapper = mock(GenreDataMapper::class.java)
    private val mockMemory = mock(MemoryCache::class.java)
    private val mockRemote = mock(RemoteStore::class.java)

    private val repository = RestaurantsDataRepository(
        mockRemote,
        mockMemory,
        mockGenreMapper,
        mockMovieMapper
    )
    private val genreMapper = GenreDataMapper()
    private val movieMapper = MovieDataMapper(
        genreMapper,
        PosterDataMapper()
    )

    @Test
    fun testGetGenresFromMemoryCompletes() {
        val entity = MoviesDataFactory.makeGenreEntity()
        val actual = genreMapper.mapFromEntity(entity)

        stubMemoryCache(entity)
        stubRemoteDataStoreEmptyGetGenres()
        stubGenreDataMapper(actual)


        repository.getGenres()
            .test()
            .assertComplete()
    }

    @Test
    fun testGetGenresFromMemoryReturnsValue() {
        val entity = MoviesDataFactory.makeGenreEntity()
        val actual = genreMapper.mapFromEntity(entity)

        stubMemoryCache(entity)
        stubRemoteDataStoreEmptyGetGenres()
        stubGenreDataMapper(actual)


        repository.getGenres()
            .test()
            .assertValue(listOf(actual))
    }

    @Test
    fun testGetGenresFromMemoryCallStore() {
        val entity = MoviesDataFactory.makeGenreEntity()
        val actual = genreMapper.mapFromEntity(entity)

        stubMemoryCache(entity)
        stubRemoteDataStoreEmptyGetGenres()
        stubGenreDataMapper(actual)


        repository.getGenres()
            .test()

        val order = inOrder(mockMemory, mockGenreMapper, mockRemote)
        order.verify(mockMemory, times(1)).getGenreEntities()
        order.verify(mockGenreMapper, times(1)).mapFromEntity(entity)
        order.verify(mockRemote, never()).getGenreEntities()
        order.verify(mockMemory, never()).cacheGenres(me.mfathy.task.any())
    }

    @Test
    fun testGetGenresFromRemoteCompletes() {
        val entity = MoviesDataFactory.makeGenreEntity()
        val actual = genreMapper.mapFromEntity(entity)

        stubEmptyMemoryCache()
        stubRemoteDataStoreGetGenres(entity)
        stubGenreDataMapper(actual)

        repository.getGenres()
            .test()
            .assertComplete()
    }

    @Test
    fun testGetGenresFromRemoteReturnsValue() {
        val entity = MoviesDataFactory.makeGenreEntity()
        val actual = genreMapper.mapFromEntity(entity)

        stubEmptyMemoryCache()
        stubRemoteDataStoreGetGenres(entity)
        stubGenreDataMapper(actual)


        repository.getGenres()
            .test()
            .assertValue(listOf(actual))
    }

    @Test
    fun testGetGenresFromRemoteCallStore() {
        val entity = MoviesDataFactory.makeGenreEntity()
        val actual = genreMapper.mapFromEntity(entity)

        stubEmptyMemoryCache()
        stubRemoteDataStoreGetGenres(entity)
        stubGenreDataMapper(actual)


        repository.getGenres()
            .test()

        val order = inOrder(mockMemory, mockGenreMapper, mockRemote)
        order.verify(mockRemote, times(1)).getGenreEntities()
        order.verify(mockMemory, times(1)).getGenreEntities()
        order.verify(mockMemory, times(1)).cacheGenres(me.mfathy.task.any())
        order.verify(mockGenreMapper, times(1)).mapFromEntity(entity)
    }

    @Test
    fun testGetMoviesCompletes() {
        val entity = MoviesDataFactory.makeMovieEntity()
        val actual = movieMapper.mapFromEntity(entity)

        stubRemoteDataStoreGetMovies(entity)
        stubMoviesDataMapper(actual)

        repository.getMovies("Drama", 1, 10)
            .test()
            .assertComplete()
    }

    @Test
    fun testGetMoviesReturnsValue() {
        val entity = MoviesDataFactory.makeMovieEntity()
        val actual = movieMapper.mapFromEntity(entity)

        stubRemoteDataStoreGetMovies(entity)
        stubMoviesDataMapper(actual)

        repository.getMovies("Drama", 1, 10)
            .test()
            .assertValue(listOf(actual, actual))
    }

    @Test
    fun testGetMoviesReturnsCallStore() {
        val entity = MoviesDataFactory.makeMovieEntity()
        val actual = movieMapper.mapFromEntity(entity)

        stubRemoteDataStoreGetMovies(entity)
        stubMoviesDataMapper(actual)

        repository.getMovies("Drama", 1, 10)
            .test()

        verify(mockRemote, times(1)).getMovieEntities(anyString(), anyInt(), anyInt())
        verify(mockMovieMapper, times(2)).mapFromEntity(me.mfathy.task.any())
    }

    @Test
    fun testBookmarkMovieCompletes() {

        val movie = MoviesDataFactory.makeMovie()

        stubMemoryCacheBookmarkMovie()

        repository.bookmarkMovie(movie)
            .test()
            .assertComplete()
    }

    @Test
    fun testBookmarkMovieCallStore() {

        val movie = MoviesDataFactory.makeMovie()

        stubMemoryCacheBookmarkMovie()

        repository.bookmarkMovie(movie)
            .test()

        verify(mockMemory).bookmark(me.mfathy.task.any())
    }

    @Test
    fun testUnBookmarkMovieCompletes() {

        val movie = MoviesDataFactory.makeMovie()

        stubMemoryCacheUnBookmarkMovie()

        repository.unBookmarkMovie(movie)
            .test()
            .assertComplete()
    }

    @Test
    fun testUnBookmarkMovieCallStore() {

        val movie = MoviesDataFactory.makeMovie()

        stubMemoryCacheUnBookmarkMovie()

        repository.unBookmarkMovie(movie)
            .test()

        verify(mockMemory).unBookmark(me.mfathy.task.any())
    }

    @Test
    fun testGetFavoritesCompletes() {

        val movie = MoviesDataFactory.makeMovie()
        val movieEntity = MoviesDataFactory.makeMovieEntity()
        stubMemoryCacheGetFavorites(movieEntity)

        stubMoviesDataMapper(movie)

        repository.getFavorites()
            .test()
            .assertComplete()
    }

    @Test
    fun testGetFavoritesReturnsData() {

        val movie = MoviesDataFactory.makeMovie()
        val movieEntity = MoviesDataFactory.makeMovieEntity()
        stubMemoryCacheGetFavorites(movieEntity)

        stubMoviesDataMapper(movie)

        repository.getFavorites()
            .test()
            .assertValue(listOf(movie))
    }

    @Test
    fun testGetFavoritesCallStore() {

        val movie = MoviesDataFactory.makeMovie()
        val movieEntity = MoviesDataFactory.makeMovieEntity()
        stubMemoryCacheGetFavorites(movieEntity)

        stubMoviesDataMapper(movie)

        repository.getFavorites()
            .test()

        verify(mockMemory).getFavorites()
        verify(mockMovieMapper).mapFromEntity(me.mfathy.task.any())
    }

    private fun stubMemoryCacheGetFavorites(movieEntity: MovieEntity) {
        `when`(mockMemory.getFavorites()).thenReturn(Single.just(listOf(movieEntity)))
    }

    private fun stubMemoryCacheBookmarkMovie() {
        `when`(mockMemory.bookmark(me.mfathy.task.any())).thenReturn(Completable.complete())
    }

    private fun stubMemoryCacheUnBookmarkMovie() {
        `when`(mockMemory.unBookmark(me.mfathy.task.any())).thenReturn(Completable.complete())
    }

    private fun stubMoviesDataMapper(movie: Movie) {
        `when`(mockMovieMapper.mapFromEntity(me.mfathy.task.any())).thenReturn(movie)
    }

    private fun stubRemoteDataStoreGetMovies(entity: MovieEntity) {
        `when`(mockRemote.getMovieEntities(anyString(), anyInt(), anyInt())).thenReturn(
            Flowable.just(
                MovieResponse(
                    listOf(entity, entity)
                )
            )
        )
    }

    private fun stubMemoryCache(entity: GenreEntity) {
        `when`(mockMemory.getGenreEntities()).thenReturn(Single.just(listOf(entity)))
    }

    private fun stubEmptyMemoryCache() {
        `when`(mockMemory.getGenreEntities()).thenReturn(Single.just(listOf()))
    }

    private fun stubRemoteDataStoreEmptyGetGenres() {
        `when`(mockRemote.getGenreEntities()).thenReturn(Single.just(listOf()))
    }

    private fun stubRemoteDataStoreGetGenres(entity: GenreEntity) {
        `when`(mockRemote.getGenreEntities()).thenReturn(Single.just(listOf(entity)))
    }

    private fun stubGenreDataMapper(genre: Genre) {
        `when`(mockGenreMapper.mapFromEntity(me.mfathy.task.any())).thenReturn(genre)
    }

}