package me.mfathy.task.data.store.memory

import me.mfathy.task.data.store.DataStore
import io.reactivex.Completable
import io.reactivex.Single
import me.mfathy.task.data.model.Restaurant

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Memory Data store contract for manging memory data store.
 */
interface MemoryCache : DataStore {

    /**
     * Destroys all memory caches.
     */
    fun destroy()

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