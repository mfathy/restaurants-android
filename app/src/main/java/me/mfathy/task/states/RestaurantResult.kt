package me.mfathy.task.states

import me.mfathy.task.data.model.Restaurant

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Sealed class for get restaurants result handling.
 */
sealed class RestaurantResult {
    data class OnSuccess(val data: List<Restaurant>) : RestaurantResult()
    data class OnFailure(val error: DataException) : RestaurantResult()
    object OnLoading : RestaurantResult()
}