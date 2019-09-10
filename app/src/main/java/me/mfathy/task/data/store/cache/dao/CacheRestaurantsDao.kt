package me.mfathy.task.data.store.cache.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.store.cache.models.CachedRestaurant

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
@Dao
abstract class CacheRestaurantsDao {

    /**
     * Bookmarks a restaurant by saving it to user's favorites.
     */
    @Insert
    abstract fun bookmark(entity: CachedRestaurant)

    /**
     * Un-bookmarks a restaurant by deleting it from user's favorites.
     */
    @Delete
    abstract fun unBookmark(entity: CachedRestaurant)

    /**
     * Returns all user's favorite restaurants.
     */
    @Query("SELECT * FROM restaurants")
    abstract fun getFavorites(): Single<List<CachedRestaurant>>
}