package me.mfathy.task.data.mapper

import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.store.cache.models.CachedRestaurant

open class CachedRestaurantMapper : EntityMapper<Restaurant, CachedRestaurant> {
    override fun mapFromEntity(entity: Restaurant): CachedRestaurant = CachedRestaurant(
        name = entity.name, isFavorite = entity.isFavorite
    )

    override fun mapToEntity(domain: CachedRestaurant): Restaurant = Restaurant(
        name = domain.name, isFavorite = domain.isFavorite
    )
}