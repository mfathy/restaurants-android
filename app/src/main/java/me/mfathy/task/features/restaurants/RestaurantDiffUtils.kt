package me.mfathy.task.features.restaurants

import androidx.recyclerview.widget.DiffUtil
import me.mfathy.task.data.model.Restaurant
import javax.inject.Inject


/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * RestaurantsAdapter Diff utils
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
}