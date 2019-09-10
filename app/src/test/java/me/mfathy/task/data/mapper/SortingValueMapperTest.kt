package me.mfathy.task.data.mapper

import konveyor.base.randomBuild
import org.junit.Assert
import org.junit.Test

import me.mfathy.task.data.model.Sorting
import me.mfathy.task.data.store.remote.models.SortingValues
import kotlin.test.assertEquals

class SortingValueMapperTest {

    private var sortingValueMapper = SortingValueMapper()

    @Test
    @Throws(Exception::class)
    fun testMapFromEntity_MapsData() {
        val entity = randomBuild(Sorting::class.java)
        val result = sortingValueMapper.mapFromEntity(entity)

        assertEqualsData(entity, result)
    }

    @Test
    @Throws(Exception::class)
    fun testMapToEntity_MapsData() {
        val type = randomBuild(SortingValues::class.java)
        val result = sortingValueMapper.mapToEntity(type)

        assertEqualsData(result, type)
    }

    private fun assertEqualsData(entity: Sorting, result: SortingValues) {
        assertEquals(entity.averageProductPrice, result.averageProductPrice)
        assertEquals(entity.bestMatch, result.bestMatch)
        assertEquals(entity.deliveryCosts, result.deliveryCosts)
        assertEquals(entity.distance, result.distance)
        assertEquals(entity.minCost, result.minCost)
        assertEquals(entity.newest, result.newest)
        assertEquals(entity.ratingAverage, result.ratingAverage)
    }
}
