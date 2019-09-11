package me.mfathy.task.data.repository

import io.reactivex.Completable
import io.reactivex.Single
import me.mfathy.task.data.model.Restaurant

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Data repository contract
 */
interface RestaurantsRepository {

    /**
     * Return a single which will emits a list of restaurants otherwise an error.
     */
    fun getRestaurants(): Single<List<Restaurant>>

    /**
     * Return a Completable which complete bookmark operation or error.
     * @param restaurant to be bookmarked
     */
    fun bookmarkRestaurant(restaurant: Restaurant): Completable

    /**
     * Return a Completable which complete un-bookmark operation or error.
     * @param restaurant to be un-bookmarked
     */
    fun unBookmarkRestaurant(restaurant: Restaurant): Completable

    /**
     * Returns a Single of list of restaurants which will emit the list or an error.
     */
    fun getFavorites(): Single<List<Restaurant>>
}