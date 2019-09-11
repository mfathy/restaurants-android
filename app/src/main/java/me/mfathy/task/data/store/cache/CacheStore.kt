package me.mfathy.task.data.store.cache

import io.reactivex.Completable
import io.reactivex.Single
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.store.DataStore

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Cache Data store contract for manging local data store.
 */
interface CacheStore : DataStore {

    /**
     * Bookmarks a restaurant by saving it to user's favorites.
     */
    fun bookmark(entity: Restaurant): Completable

    /**
     * Un-bookmarks a restaurant by deleting it from user's favorites.
     */
    fun unBookmark(entity: Restaurant): Completable

    /**
     * Returns all user's favorite restaurants.
     */
    fun getFavorites(): Single<List<Restaurant>>
}