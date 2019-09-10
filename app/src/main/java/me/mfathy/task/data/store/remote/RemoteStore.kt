package me.mfathy.task.data.store.remote

import io.reactivex.Single
import me.mfathy.task.data.store.DataStore
import me.mfathy.task.data.store.remote.models.RestaurantsResponse

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Data store contract for remote.
 */
interface RemoteStore : DataStore {
    /**
     * Return a Single which will emits a list of restaurants otherwise an error.
     */
    fun getRestaurants(): Single<RestaurantsResponse>
}