package me.mfathy.task.interactors.restaurants

import io.reactivex.Single
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.repository.RestaurantsRepository
import me.mfathy.task.interactors.base.SingleUseCase
import me.mfathy.task.extensions.rx.subscribeAndObserve
import javax.inject.Inject

open class GetRestaurants @Inject constructor(
    private val repository: RestaurantsRepository
) : SingleUseCase<List<Restaurant>>() {
    override fun buildUseCaseSingle(): Single<List<Restaurant>> {
        return repository.getRestaurants()
            .subscribeAndObserve()
    }
}