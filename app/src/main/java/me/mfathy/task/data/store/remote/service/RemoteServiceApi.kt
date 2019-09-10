package me.mfathy.task.data.store.remote.service

import io.reactivex.Single
import me.mfathy.task.data.store.remote.models.RestaurantsResponse

/**
 * Created by Mohammed Fathy
 * dev.mfathy@gmail.com
 *
 * RemoteServiceApi retrofit end point to access remote server api.
 */
interface RemoteServiceApi {
    fun getRestaurants(): Single<RestaurantsResponse>
}