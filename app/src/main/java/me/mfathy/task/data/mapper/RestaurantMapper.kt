package me.mfathy.task.data.mapper

import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.store.remote.models.RestaurantItem
import javax.inject.Inject

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * RestaurantMapper maps between Restaurant & RestaurantItem
 */
open class RestaurantMapper @Inject constructor(
    private val sortingMapper: SortingValueMapper
) : EntityMapper<Restaurant, RestaurantItem> {
    override fun mapFromEntity(entity: Restaurant): RestaurantItem = RestaurantItem(
        name = entity.name,
        sortingValues = sortingMapper.mapFromEntity(entity.sortingValues),
        status = entity.status
    )

    override fun mapToEntity(domain: RestaurantItem): Restaurant = Restaurant(
        name = domain.name,
        sortingValues = sortingMapper.mapToEntity(domain.sortingValues),
        status = domain.status,
        isFavorite = false
    )

}