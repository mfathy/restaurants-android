package me.mfathy.task.interactors.favorites

import io.reactivex.Completable
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.repository.RestaurantsRepository
import me.mfathy.task.extensions.rx.subscribeAndObserve
import me.mfathy.task.interactors.base.CompletableUseCase
import javax.inject.Inject

open class UnBookmarkRestaurant @Inject constructor(
    private val repository: RestaurantsRepository
) : CompletableUseCase<UnBookmarkRestaurant.Params>() {
    override fun buildUseCaseCompletable(params: Params): Completable =
        repository.unBookmarkRestaurant(params.restaurant)
            .subscribeAndObserve()

    data class Params(val restaurant: Restaurant)

}
