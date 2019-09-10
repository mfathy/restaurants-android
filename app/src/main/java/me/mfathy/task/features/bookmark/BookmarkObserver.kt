package me.mfathy.task.features.bookmark

import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.features.base.BaseViewModel
import me.mfathy.task.states.BookmarkResult

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Generic BookmarkObserver to handle subscription and emit changes to live data.
 */
class BookmarkObserver(
    private val viewModel: BaseViewModel,
    private val restaurant: Restaurant,
    private val position: Int,
    private val isBookmarked: Boolean
) :
    CompletableObserver {
    override fun onSubscribe(d: Disposable) {
        viewModel.addDisposables(d)
    }

    override fun onComplete() {
        restaurant.isFavorite = isBookmarked
        viewModel.getBookmarkRestaurantLiveData()
            .postValue(BookmarkResult.OnSuccess(Pair(position, restaurant)))
    }

    override fun onError(e: Throwable) {
        viewModel.getBookmarkRestaurantLiveData().postValue(BookmarkResult.OnFailure)
    }

}