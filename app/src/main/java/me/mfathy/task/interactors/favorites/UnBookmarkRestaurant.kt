package me.mfathy.task.interactors.favorites

import me.mfathy.task.interactors.base.CompletableUseCase
import me.mfathy.task.data.repository.RestaurantsRepository
import me.mfathy.task.extensions.rx.subscribeAndObserve
import io.reactivex.Completable
import me.mfathy.task.data.model.Restaurant
import javax.inject.Inject

class UnBookmarkRestaurant @Inject constructor(
    private val repository: RestaurantsRepository
) : CompletableUseCase<UnBookmarkRestaurant.Params>() {
    override fun buildUseCaseCompletable(params: Params): Completable =
        repository.unBookmarkRestaurant(params.restaurant)
            .subscribeAndObserve()

    data class Params(val restaurant: Restaurant)

}
