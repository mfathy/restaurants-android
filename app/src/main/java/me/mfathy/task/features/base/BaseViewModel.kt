package me.mfathy.task.features.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.mfathy.task.interactors.favorites.BookmarkRestaurant
import me.mfathy.task.interactors.favorites.UnBookmarkRestaurant
import me.mfathy.task.features.bookmark.BookmarkObserver
import me.mfathy.task.states.BookmarkResult
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import me.mfathy.task.data.model.Restaurant
import javax.inject.Inject

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * BaseViewModel to manage disposable for all sub view models.
 */
open class BaseViewModel @Inject constructor(
    private val bookmarkRestaurant: BookmarkRestaurant,
    private val unBookmarkMovie: UnBookmarkRestaurant
) : ViewModel() {

    private val disposables = CompositeDisposable()

    /**
     * Add Rx disposables to be easy for clean up.
     */
    open fun addDisposables(disposable: Disposable) {
        disposables.add(disposable)
    }

    /**
     * Remove disposables.
     */
    open fun clearDisposables() {
        if (!disposables.isDisposed) disposables.dispose()
    }

    override fun onCleared() {
        super.onCleared()
        clearDisposables()
    }

    //region Bookmarking
    private val bookmarkMovieLiveData: MutableLiveData<BookmarkResult> = MutableLiveData()

    fun getBookmarkRestaurantLiveData() = bookmarkMovieLiveData


    fun setBookmarkedRestaurant(restaurant: Restaurant, position: Int) {
        restaurant.isFavorite = true
        bookmarkRestaurant.execute(
            BookmarkObserver(
                this,
                restaurant,
                position,
                true
            ),
            BookmarkRestaurant.Params(restaurant)
        )
    }

    fun setUnBookmarkedRestaurant(restaurant: Restaurant, position: Int) {
        restaurant.isFavorite = false
        unBookmarkMovie.execute(
            BookmarkObserver(
                this,
                restaurant,
                position,
                false
            ),
            UnBookmarkRestaurant.Params(restaurant)
        )
    }

    //endregion

}