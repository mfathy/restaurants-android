package me.mfathy.task.data.store.cache.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */

@Entity(tableName = "restaurants")
data class CachedRestaurant(
    @PrimaryKey
    @ColumnInfo(name = "rest_name")
    val name: String = "",
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean
)
