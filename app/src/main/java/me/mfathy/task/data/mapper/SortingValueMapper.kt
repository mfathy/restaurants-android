package me.mfathy.task.data.mapper

import me.mfathy.task.data.model.Sorting
import me.mfathy.task.data.store.remote.models.SortingValues

class SortingValueMapper : EntityMapper<Sorting, SortingValues> {
    override fun mapFromEntity(entity: Sorting): SortingValues = SortingValues(
        averageProductPrice = entity.averageProductPrice,
        bestMatch = entity.bestMatch,
        deliveryCosts = entity.deliveryCosts,
        distance = entity.distance,
        minCost = entity.minCost,
        newest = entity.newest,
        popularity = entity.popularity,
        ratingAverage = entity.ratingAverage
    )

    override fun mapToEntity(domain: SortingValues): Sorting = Sorting(
        averageProductPrice = domain.averageProductPrice,
        bestMatch = domain.bestMatch,
        deliveryCosts = domain.deliveryCosts,
        distance = domain.distance,
        minCost = domain.minCost,
        newest = domain.newest,
        popularity = domain.popularity,
        ratingAverage = domain.ratingAverage
    )
}