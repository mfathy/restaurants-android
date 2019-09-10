package me.mfathy.task.features.search

import androidx.lifecycle.MutableLiveData
import io.reactivex.observers.DisposableSingleObserver
import me.mfathy.task.data.model.OpeningState
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.model.SortingKeys
import me.mfathy.task.features.base.BaseViewModel
import me.mfathy.task.interactors.favorites.BookmarkRestaurant
import me.mfathy.task.interactors.favorites.UnBookmarkRestaurant
import me.mfathy.task.interactors.restaurants.GetRestaurants
import me.mfathy.task.states.DataException
import me.mfathy.task.states.RestaurantResult
import java.util.*
import javax.inject.Inject


/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * SearchActivity view-model
 */
class SearchViewModel @Inject constructor(
    private val getRestaurants: GetRestaurants,
    bookmarkRestaurant: BookmarkRestaurant,
    unBookmarkMovie: UnBookmarkRestaurant
) : BaseViewModel(bookmarkRestaurant, unBookmarkMovie) {

    /**
     * Selected sorting option for restaurants list.
     */
    private val sortingOptionLiveData: MutableLiveData<SortingKeys> = MutableLiveData()

    fun getSortingOptionLiveData() = sortingOptionLiveData

    private val defaultSortingOption = SortingKeys.POPULARITY

    fun setSelectedSortingOption(selectedOption: SortingKeys) {
        sortingOptionLiveData.postValue(selectedOption)
    }

    /**
     * Restaurants results Live data to post changes to UI.
     */
    private val restaurantsLiveData: MutableLiveData<RestaurantResult> = MutableLiveData()

    fun getRestaurantsLiveData() = restaurantsLiveData

    fun fetchRestaurants() {
        //  Update loading progress.
        showProgress()

        //  Select sorting option.
        sortingOptionLiveData.postValue(defaultSortingOption)

        addDisposables(
            getRestaurants.execute(GetRestaurantsObserver())
        )
    }

    private fun showProgress() {
        restaurantsLiveData.postValue(RestaurantResult.OnLoading)
    }

    fun buildSortByOptionsComparator(sortingKey: SortingKeys?): Comparator<Restaurant> {
        val byFavorites = compareByDescending<Restaurant> { restaurant -> restaurant.isFavorite }
        val byOpeningState: (Restaurant) -> OpeningState = { restaurant ->
            val status =
                restaurant.status.toUpperCase(Locale.getDefault()).replace(" ", "_")
            OpeningState.valueOf(status)
        }

        return when (sortingKey) {
            SortingKeys.AVERAGE_PRODUCT_PRICE -> byFavorites
                .thenBy(byOpeningState)
                .thenByDescending { restaurant -> restaurant.sortingValues.averageProductPrice }
            SortingKeys.BEST_MATCH -> byFavorites
                .thenBy(byOpeningState)
                .thenByDescending { restaurant -> restaurant.sortingValues.bestMatch }
            SortingKeys.DISTANCE -> byFavorites
                .thenBy(byOpeningState)
                .thenByDescending { restaurant -> restaurant.sortingValues.distance }
            SortingKeys.DELIVERY_COSTS -> byFavorites
                .thenBy(byOpeningState)
                .thenByDescending { restaurant -> restaurant.sortingValues.deliveryCosts }
            SortingKeys.POPULARITY -> byFavorites
                .thenBy(byOpeningState)
                .thenByDescending { restaurant -> restaurant.sortingValues.popularity }
            SortingKeys.NEWEST -> byFavorites
                .thenBy(byOpeningState)
                .thenByDescending { restaurant -> restaurant.sortingValues.newest }
            SortingKeys.MIN_COST -> byFavorites
                .thenBy(byOpeningState)
                .thenByDescending { restaurant -> restaurant.sortingValues.minCost }
            SortingKeys.RATING_AVERAGE -> byFavorites
                .thenBy(byOpeningState)
                .thenByDescending { restaurant -> restaurant.sortingValues.ratingAverage }
            //  Default.
            null -> byFavorites
                .thenBy(byOpeningState)
                .thenByDescending { restaurant -> restaurant.sortingValues.bestMatch }
        }
    }

    inner class GetRestaurantsObserver : DisposableSingleObserver<List<Restaurant>>() {
        override fun onSuccess(list: List<Restaurant>) {
            val sortByOptionComparator = buildSortByOptionsComparator(defaultSortingOption)

            val mutableList = list.toMutableList()
            mutableList.sortWith(sortByOptionComparator)

            restaurantsLiveData.postValue(RestaurantResult.OnSuccess(mutableList))
        }

        override fun onError(e: Throwable) {
            restaurantsLiveData.postValue(
                RestaurantResult.OnFailure(
                    DataException.NotFoundException
                )
            )
        }
    }
}