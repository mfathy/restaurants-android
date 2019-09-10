package me.mfathy.task.data.store.memory

import me.mfathy.task.factory.MoviesDataFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@RunWith(RobolectricTestRunner::class)
class MemoryCacheTest {

    private val memoryCache = MemoryDataStore()

    @Test
    fun testGetGenreEntitiesCompletes() {
        memoryCache.getGenreEntities()
            .test()
            .assertComplete()
    }

    @Test
    fun testGetGenreEntitiesReturnsEmptyList() {
        memoryCache.cacheGenres(listOf())
        memoryCache.getGenreEntities()
            .test()
            .assertValue(listOf())
    }

    @Test
    fun testGetGenreEntitiesReturnsData() {
        val entity = MoviesDataFactory.makeGenreEntity()

        memoryCache.cacheGenres(listOf(entity))
        memoryCache.getGenreEntities()
            .test()
            .assertValue(listOf(entity))
    }

    @Test
    fun testDestroyClearsMemoryCache() {
        val entity = MoviesDataFactory.makeGenreEntity()

        memoryCache.cacheGenres(listOf(entity))
        memoryCache.destroy()

        memoryCache.getGenreEntities()
            .test()
            .assertValue(listOf())
    }

    @Test
    fun testBookmarkMovieCompletes() {
        val movieEntity = MoviesDataFactory.makeMovieEntity()

        memoryCache.bookmark(movieEntity)
            .test()
            .assertComplete()
    }

    @Test
    fun testUnBookmarkMovieCompletes() {
        val movieEntity = MoviesDataFactory.makeMovieEntity()

        memoryCache.unBookmark(movieEntity)
            .test()
            .assertComplete()
    }

    @Test
    fun testGetFavoritesCompletes() {

        memoryCache.getFavorites()
            .test()
            .assertComplete()
    }

    @Test
    fun testGetFavoritesReturnsData() {
        val movieEntity = MoviesDataFactory.makeMovieEntity()

        memoryCache.bookmark(movieEntity).test()

        memoryCache.getFavorites()
            .test()
            .assertValue(listOf(movieEntity))
    }

    @Test
    fun testGetFavoritesReturnsEmpty() {


        memoryCache.getFavorites()
            .test()
            .assertValue(listOf())
    }

}