package me.mfathy.task.data.mapper

import me.mfathy.task.data.model.GenreEntity
import me.mfathy.task.data.model.MovieEntity
import me.mfathy.task.data.model.PosterEntity
import me.mfathy.task.domain.model.Genre
import me.mfathy.task.domain.model.Movie
import me.mfathy.task.domain.model.Poster
import me.mfathy.task.factory.MoviesDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Unit test for GenreDataMapper
 */
class MovieDataMapperTest {

    private val mapper = MovieDataMapper(
        GenreDataMapper(),
        PosterDataMapper()
    )

    @Test
    fun testMapFromEntityMapsData() {

        val entity = MoviesDataFactory.makeMovieEntity()
        val actual = mapper.mapFromEntity(entity)

        assertEqualsData(entity, actual)
    }

    @Test
    fun testMapToEntityMapsData() {
        val movie = MoviesDataFactory.makeMovie()
        val result = mapper.mapToEntity(movie)

        assertEqualsData(result, movie)
    }

    private fun assertEqualsData(entity: MovieEntity, actual: Movie) {
        assertEquals(entity.id, actual.id)
        assertEquals(entity.rating, actual.rating, 0.5)
        assertEquals(entity.ratingCount, actual.ratingCount)
        assertEquals(entity.title, actual.title)
        assertPosterEquals(entity.poster, actual.poster)
        assertGenreEquals(entity.genres?.first(), actual.genres?.first())
        assertEquals(entity.id, actual.id)
    }

    private fun assertPosterEquals(entity: PosterEntity, actual: Poster) {
        assertEquals(entity.fullPath, actual.fullPath)
        assertEquals(entity.size, actual.size)
    }

    private fun assertGenreEquals(entity: GenreEntity?, actual: Genre?) {
        assertEquals(entity?.id, actual?.id)
        assertEquals(entity?.name, actual?.name)
    }
}