package me.mfathy.task.features.restaurants

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_restaurants.*
import me.mfathy.task.BuildConfig
import me.mfathy.task.R
import me.mfathy.task.data.model.Restaurant
import me.mfathy.task.data.model.SortingKeys
import me.mfathy.task.features.base.BaseActivity
import me.mfathy.task.idlingResource.SimpleIdlingResource
import me.mfathy.task.injection.factory.ViewModelFactory
import me.mfathy.task.states.BookmarkResult
import me.mfathy.task.states.DataException
import me.mfathy.task.states.RestaurantResult
import javax.inject.Inject

/**
 * Restaurants Activity.
 */
class RestaurantsActivity : BaseActivity(),
    RestaurantsAdapter.OnAttachRestaurantsListener, PopupMenu.OnMenuItemClickListener {

    // The Idling Resource which will be null in production.
    private lateinit var mIdlingResource: SimpleIdlingResource

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RestaurantsViewModel
    private lateinit var restaurantsAdapter: RestaurantsAdapter

    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var searchView: SearchView

    //region Activity methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurants)

        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(RestaurantsViewModel::class.java)
        if (BuildConfig.DEBUG) {
            viewModel.setIdlingResource(getIdlingResource())
        }

        initViews()

        observeRestaurantsResultLiveData()
        observeBookmarkLiveData()
        observeSortingLiveData()

        viewModel.fetchRestaurants()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.setSearchableInfo(
            searchManager
                .getSearchableInfo(componentName)
        )
        searchView.maxWidth = Integer.MAX_VALUE

        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                restaurantsAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                restaurantsAdapter.filter.filter(query)
                return false
            }
        })
        return true
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        val sortingKey = item?.itemId?.let { getSortingKey(it) }
        sortingKey?.let { viewModel.setSelectedSortingOption(it) }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when {
            item.itemId == R.id.action_search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }
    //endregion

    //region Callbacks
    override fun bookmarkRestaurant(restaurant: Restaurant, position: Int) {
        viewModel.setBookmarkedRestaurant(restaurant, position)
    }
    //endregion

    //region Others
    fun showSortingValues(v: View) {
        showPopup(v)
    }

    private fun initViews() {

        layoutManager = LinearLayoutManager(this)
        restaurantsAdapter = RestaurantsAdapter(this, mutableListOf(), mutableListOf(), this)

        recyclerView_List.layoutManager = layoutManager
        recyclerView_List.adapter = restaurantsAdapter

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView_List.context,
            DividerItemDecoration.VERTICAL
        )
        recyclerView_List.addItemDecoration(dividerItemDecoration)

        swipe_refresh.setOnRefreshListener {
            viewModel.fetchRestaurants()
        }

        recyclerView_List.setHasFixedSize(true)

    }

    private fun showPopup(v: View) {
        PopupMenu(this, v).apply {
            // MainActivity implements OnMenuItemClickListener
            setOnMenuItemClickListener(this@RestaurantsActivity)
            inflate(R.menu.menu_sort_actions)
            show()
        }
    }

    private fun getSortingKey(key: Int): SortingKeys {
        return when (key) {
            R.id.action_averageProductPrice -> SortingKeys.AVERAGE_PRODUCT_PRICE
            R.id.action_bestMatch -> SortingKeys.BEST_MATCH
            R.id.action_deliveryCosts -> SortingKeys.DELIVERY_COSTS
            R.id.action_distance -> SortingKeys.DISTANCE
            R.id.action_minCost -> SortingKeys.MIN_COST
            R.id.action_newest -> SortingKeys.NEWEST
            R.id.action_popularity -> SortingKeys.POPULARITY
            R.id.action_ratingAverage -> SortingKeys.RATING_AVERAGE

            else -> SortingKeys.BEST_MATCH
        }

    }

    private fun getSortingString(key: SortingKeys): String {
        return when (key) {
            SortingKeys.AVERAGE_PRODUCT_PRICE -> getString(R.string.action_averageProductPrice)
            SortingKeys.BEST_MATCH -> getString(R.string.action_bestMatch)
            SortingKeys.DISTANCE -> getString(R.string.action_distance)
            SortingKeys.DELIVERY_COSTS -> getString(R.string.action_deliveryCosts)
            SortingKeys.POPULARITY -> getString(R.string.action_popularity)
            SortingKeys.NEWEST -> getString(R.string.action_newest)
            SortingKeys.MIN_COST -> getString(R.string.action_minCost)
            SortingKeys.RATING_AVERAGE -> getString(R.string.action_ratingAverage)
        }
    }

    private fun observeSortingLiveData() {

        viewModel.getSortingOptionLiveData().observe(this, Observer {
            val comparator = viewModel.buildSortByOptionsComparator(it)
            textView_selected_sort.text = getSortingString(it)
            restaurantsAdapter.sortItems(comparator)
        })
    }

    private fun observeRestaurantsResultLiveData() {
        viewModel.getRestaurantsLiveData().observe(this, Observer {
            it?.let { result -> handleRestaurantsResult(result) }
        })
    }

    private fun observeBookmarkLiveData() {
        viewModel.getBookmarkRestaurantLiveData().observe(this, Observer {
            it?.let { result -> handleBookmarkResult(result) }
        })
    }

    private fun handleRestaurantsResult(result: RestaurantResult) {

        when (result) {
            is RestaurantResult.OnSuccess -> {
                handleRestaurantsResultSuccess(result)
            }
            is RestaurantResult.OnFailure -> {
                handleRestaurantsResultFailure(result)
            }
            is RestaurantResult.OnLoading -> {
                updateViewVisibility(
                    showLoading = true
                )
            }
        }
    }

    private fun handleBookmarkResult(result: BookmarkResult) {

        when (result) {
            is BookmarkResult.OnSuccess -> {
                val restaurantPair = result.data
                restaurantsAdapter.updateRestaurant(restaurantPair)
            }
            is BookmarkResult.OnFailure -> {
                showToast(getString(R.string.error_bookmark_failed))
            }
        }
    }

    private fun handleRestaurantsResultSuccess(result: RestaurantResult.OnSuccess) {
        if (result.data.isEmpty()) {
            textView_List_callToAction.text = getString(R.string.error_no_restaurants)
            updateViewVisibility(
                showError = true
            )

            return
        }

        restaurantsAdapter.updateRestaurants(result.data)
        updateViewVisibility(
            showData = true
        )
    }

    private fun handleRestaurantsResultFailure(result: RestaurantResult.OnFailure) {

        updateViewVisibility(
            showError = true
        )

        when (result.error) {
            is DataException.NotFoundException -> {
                textView_List_callToAction.text = getString(R.string.error_genre_not_found)
            }

            is DataException.NetworkException -> {
                textView_List_callToAction.text = getString(R.string.error_no_network)
            }

            is DataException.ConnectionException -> {
                textView_List_callToAction.text = getString(R.string.error_no_connection_to_server)
            }
        }

    }
    //endregion

    /**
     * Only called from test, creates and returns a new [SimpleIdlingResource].
     */
    @VisibleForTesting
    fun getIdlingResource(): SimpleIdlingResource {
        if (!::mIdlingResource.isInitialized) {
            mIdlingResource = SimpleIdlingResource()
        }
        return mIdlingResource
    }

}
