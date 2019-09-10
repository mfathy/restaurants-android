package me.mfathy.task.data.store.remote.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */
data class RestaurantsResponse(
    @SerializedName("restaurants")
    val restaurants: List<RestaurantItem>?
)

data class RestaurantItem(
    @SerializedName("sortingValues")
    val sortingValues: SortingValues,
    @SerializedName("name")
    val name: String = "",
    @SerializedName("status")
    val status: String = ""
)

data class SortingValues(
    @SerializedName("averageProductPrice")
    val averageProductPrice: Int = 0,
    @SerializedName("bestMatch")
    val bestMatch: Int = 0,
    @SerializedName("distance")
    val distance: Int = 0,
    @SerializedName("deliveryCosts")
    val deliveryCosts: Int = 0,
    @SerializedName("popularity")
    val popularity: Int = 0,
    @SerializedName("newest")
    val newest: Int = 0,
    @SerializedName("minCost")
    val minCost: Int = 0,
    @SerializedName("ratingAverage")
    val ratingAverage: Double = 0.0
)