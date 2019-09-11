package me.mfathy.task.data.store.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import me.mfathy.task.BuildConfig
import me.mfathy.task.data.store.cache.dao.CacheRestaurantsDao
import me.mfathy.task.data.store.cache.models.CachedRestaurant

/**
 * RestaurantsDatabase: the room database initializer.
 */
@Database(entities = [CachedRestaurant::class], version = 1, exportSchema = false)
abstract class RestaurantsDatabase : RoomDatabase() {

    abstract fun cachedRestaurantDao(): CacheRestaurantsDao

    companion object {

        private var INSTANCE: RestaurantsDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): RestaurantsDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            RestaurantsDatabase::class.java, BuildConfig.DATABASE_NAME
                        ).build()
                    }
                    return INSTANCE as RestaurantsDatabase
                }
            }
            return INSTANCE as RestaurantsDatabase
        }
    }

}