package me.mfathy.task.data.store.remote

import io.reactivex.Single
import me.mfathy.task.data.store.remote.models.RestaurantsResponse
import me.mfathy.task.data.store.remote.service.RemoteServiceApi
import javax.inject.Inject


/**
 * Remote Data Store implementation.
 */
open class RemoteDataStore @Inject constructor(
    private val remoteServiceApi: RemoteServiceApi
) : RemoteStore {
    override fun getRestaurants(): Single<RestaurantsResponse> {
        return remoteServiceApi.getRestaurants()
    }

}
