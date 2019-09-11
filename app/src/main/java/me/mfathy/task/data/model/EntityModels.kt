package me.mfathy.task.data.model

import java.util.*


data class Restaurant(
    val name: String = "",
    var isFavorite: Boolean,
    val status: String = "",
    val sortingValues: Sorting = Sorting()
) {

    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        val restaurant = other as Restaurant
        return name == restaurant.name
    }

    override fun hashCode(): Int {
        return Objects.hashCode(name)
    }

}

data class Sorting(
    val averageProductPrice: Int = 0,
    val bestMatch: Int = 0,
    val distance: Int = 0,
    val deliveryCosts: Int = 0,
    val popularity: Int = 0,
    val newest: Int = 0,
    val minCost: Int = 0,
    val ratingAverage: Double = 0.0
)




