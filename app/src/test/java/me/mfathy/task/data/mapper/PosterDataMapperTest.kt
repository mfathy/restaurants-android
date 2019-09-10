package me.mfathy.task.data.mapper

import me.mfathy.task.data.model.PosterEntity
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
class PosterDataMapperTest {

    private val mapper = PosterDataMapper()

    @Test
    fun testMapFromEntityMapsData() {

        val entity = MoviesDataFactory.makePosterEntity()
        val actual = mapper.mapFromEntity(entity)

        assertEqualsData(entity, actual)
    }

    @Test
    fun testMapToEntityMapsData() {
        val poster = MoviesDataFactory.makePoster()
        val result = mapper.mapToEntity(poster)

        assertEqualsData(result, poster)
    }

    private fun assertEqualsData(entity: PosterEntity, actual: Poster) {
        assertEquals(entity.fullPath, actual.fullPath)
        assertEquals(entity.size, actual.size)
    }
}