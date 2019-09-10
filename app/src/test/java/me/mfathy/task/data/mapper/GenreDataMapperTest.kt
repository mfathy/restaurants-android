package me.mfathy.task.data.mapper

import me.mfathy.task.data.model.GenreEntity
import me.mfathy.task.domain.model.Genre
import me.mfathy.task.factory.MoviesDataFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Unit test for GenreDataMapper
 */
class GenreDataMapperTest {

    private val mapper = GenreDataMapper()



    @Test
    fun testMapFromEntityMapsData() {

        val entity = MoviesDataFactory.makeGenreEntity()
        val actual = mapper.mapFromEntity(entity)

        assertEqualsData(entity, actual)
    }

    @Test
    fun testMapToEntity() {
        val genre = MoviesDataFactory.makeGenre()
        val result = mapper.mapToEntity(genre)
        assertEqualsData(result, genre)
    }

    private fun assertEqualsData(entity: GenreEntity, actual: Genre) {
        assertEquals(entity.id, actual.id)
        assertEquals(entity.name, actual.name)
    }
}