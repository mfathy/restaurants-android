package me.mfathy.task.features.search

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil
import me.mfathy.task.data.model.Restaurant
import javax.inject.Inject


/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
class RestaurantDiffUtils @Inject constructor(
    private var oldRestaurants: MutableList<Restaurant>,
    private var newRestaurants: MutableList<Restaurant>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRestaurants[oldItemPosition].name == newRestaurants[newItemPosition].name
                && oldRestaurants[oldItemPosition].isFavorite == newRestaurants[newItemPosition].isFavorite
    }

    override fun getOldListSize(): Int = oldRestaurants.size

    override fun getNewListSize(): Int = newRestaurants.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRestaurants[oldItemPosition] == newRestaurants[newItemPosition]
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldRestaurants[oldItemPosition]
        val newItem = newRestaurants[newItemPosition]

        val diff = Bundle()
        if (newItem.name != oldItem.name) {
            diff.putString("name", newItem.name)
        }
        if (newItem.isFavorite != oldItem.isFavorite) {
            diff.putBoolean("isFavorite", newItem.isFavorite)
        }
        return if (diff.size() == 0) {
            null
        } else diff
    }
}