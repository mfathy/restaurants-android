package me.mfathy.task.data.mapper

import konveyor.base.randomBuild
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.store.cache.models.CachedRestaurant
import org.junit.Test
import kotlin.test.assertEquals

class CachedRestaurantMapperTest {
    private var cachedRestaurantMapper = CachedRestaurantMapper()

    @Test
    @Throws(Exception::class)
    fun testMapFromEntity_MapsData() {

        val entity = randomBuild(Restaurant::class.java)
        val result = cachedRestaurantMapper.mapFromEntity(entity)

        assertEqualsData(entity, result)
    }

    @Test
    @Throws(Exception::class)
    fun testMapToEntity_MapsData() {
        val type = randomBuild(CachedRestaurant::class.java)
        val result = cachedRestaurantMapper.mapToEntity(type)

        assertEqualsData(result, type)
    }

    private fun assertEqualsData(entity: Restaurant, result: CachedRestaurant) {
        assertEquals(entity.isFavorite, result.isFavorite)
        assertEquals(entity.name, result.name)
    }
}
