package me.mfathy.task.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import me.mfathy.task.data.mapper.RestaurantMapper
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.store.cache.CacheStore
import me.mfathy.task.data.store.remote.RemoteStore
import me.mfathy.task.data.store.remote.models.RestaurantsResponse
import javax.inject.Inject

/**
 * Restaurants DataRepository implementation.
 */
class RestaurantsDataRepository @Inject constructor(
    private val remoteStore: RemoteStore,
    private val cacheStore: CacheStore,
    private val restaurantMapper: RestaurantMapper

) : RestaurantsRepository {
    override fun getRestaurants(): Single<List<Restaurant>> {
        return Single.zip(remoteStore.getRestaurants(),
            cacheStore.getFavorites(),
            BiFunction<RestaurantsResponse, List<Restaurant>, List<Restaurant>> { remoteResponse, cachedResponse ->

                remoteResponse.restaurants?.map { restaurant ->
                    val mappedRestaurant = restaurantMapper.mapToEntity(
                        restaurant
                    )

                    //  Update favorites.
                    if (cachedResponse.contains(mappedRestaurant)) {
                        val restaurantIndex = cachedResponse.indexOf(mappedRestaurant)
                        mappedRestaurant.isFavorite = cachedResponse[restaurantIndex].isFavorite
                    }
                    mappedRestaurant
                }!!

            }
        )
    }

    override fun bookmarkRestaurant(restaurant: Restaurant): Completable {
        return cacheStore.bookmark(restaurant)
    }

    override fun unBookmarkRestaurant(restaurant: Restaurant): Completable {
        return cacheStore.unBookmark(restaurant)
    }

    override fun getFavorites(): Single<List<Restaurant>> {
        return cacheStore.getFavorites()
    }


}