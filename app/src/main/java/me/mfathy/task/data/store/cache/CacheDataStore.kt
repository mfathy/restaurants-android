package me.mfathy.task.data.store.cache

import io.reactivex.Completable
import io.reactivex.Single
import me.mfathy.task.data.mapper.CachedRestaurantMapper
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.store.cache.db.RestaurantsDatabase
import javax.inject.Inject

class CacheDataStore @Inject constructor(
    private val database: RestaurantsDatabase,
    private val mapper:CachedRestaurantMapper
) : CacheStore {
    override fun bookmark(entity: Restaurant): Completable {
        return Completable.defer{
            database.cachedRestaurantDao().bookmark(mapper.mapFromEntity(entity))
            Completable.complete()
        }
    }

    override fun unBookmark(entity: Restaurant): Completable {
        return Completable.defer{
            database.cachedRestaurantDao().unBookmark(mapper.mapFromEntity(entity))
            Completable.complete()
        }
    }

    override fun getFavorites(): Single<List<Restaurant>> {
        return database.cachedRestaurantDao().getFavorites().map {
            list -> list.map { mapper.mapToEntity(it) }
        }
    }

}