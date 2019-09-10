package me.mfathy.task.features.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.mfathy.task.R
import me.mfathy.task.data.model.Restaurant
import java.util.*
import javax.inject.Inject


/**
 * RecyclerView adapter to redraw restaurant item.
 */
class RestaurantsAdapter @Inject constructor(
    private val mContext: Context,
    private var restaurants: MutableList<Restaurant>,
    private var filteredRestaurants: MutableList<Restaurant>,
    private val attachListener: OnAttachRestaurantsListener
) :
    RecyclerView.Adapter<RestaurantsAdapter.RestaurantViewHolder>(), Filterable {

    interface OnAttachRestaurantsListener {
        fun bookmarkRestaurant(restaurant: Restaurant, position: Int)
    }

    override fun getItemCount(): Int {
        return filteredRestaurants.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        return RestaurantViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = filteredRestaurants[position]
        holder.bindView(holder, mContext, restaurant, attachListener)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                filteredRestaurants = if (charString.isEmpty()) {
                    restaurants
                } else {
                    restaurants.filter {
                        it.name.toLowerCase(Locale.getDefault())
                            .contains(charString.toLowerCase(Locale.getDefault()))
                    }.toMutableList()
                }

                val filterResults = FilterResults()
                filterResults.values = filteredRestaurants
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredRestaurants = filterResults.values as MutableList<Restaurant>

                // refresh the list with filtered data
                val restaurantDiffUtils = RestaurantDiffUtils(restaurants, filteredRestaurants)
                val result = DiffUtil.calculateDiff(restaurantDiffUtils)
                result.dispatchUpdatesTo(this@RestaurantsAdapter)
            }
        }
    }

    fun appendRestaurants(list: List<Restaurant>) {
        resetRestaurants()
        this.restaurants.addAll(list)
        this.filteredRestaurants.addAll(list)
        notifyDataSetChanged()
    }

    fun resetRestaurants() {
        this.restaurants.clear()
        this.filteredRestaurants.clear()
    }

    fun updateRestaurant(restaurantPair: Pair<Int, Restaurant>) {
        restaurants[restaurantPair.first] = restaurantPair.second
        filteredRestaurants[restaurantPair.first] = restaurantPair.second
        notifyItemChanged(restaurantPair.first)
    }

    fun sortItems(comparator: Comparator<Restaurant>) {
        filteredRestaurants.sortWith(comparator)

        val restaurantDiffUtils = RestaurantDiffUtils(restaurants, filteredRestaurants)
        val result = DiffUtil.calculateDiff(restaurantDiffUtils)
        result.dispatchUpdatesTo(this)
    }

    class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var textViewName: TextView? = view.findViewById(R.id.textView_restaurant_name)
        var textViewOpenStatue: TextView? = view.findViewById(R.id.textView_restaurant_open_status)
        var imageViewFavoriteBtn: ImageView? = view.findViewById(R.id.imageView_ListItem_favorite)


        companion object {
            fun create(parent: ViewGroup): RestaurantViewHolder {
                val itemView = LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.listitem_restaurant, parent, false)
                return RestaurantViewHolder(itemView)
            }
        }


        fun bindView(
            holder: RestaurantViewHolder,
            mContext: Context,
            restaurant: Restaurant, attachListener: OnAttachRestaurantsListener
        ) {

            holder.textViewName?.text = restaurant.name
            holder.textViewOpenStatue?.text = restaurant.status

            holder.imageViewFavoriteBtn?.let {
                it.visibility = View.VISIBLE
                if (restaurant.isFavorite) it.setImageDrawable(
                    ContextCompat.getDrawable(
                        mContext,
                        R.drawable.ic_favorite_remove
                    )
                )
                else it.setImageDrawable(
                    ContextCompat.getDrawable(
                        mContext,
                        R.drawable.ic_favorite_add
                    )
                )

                it.setOnClickListener {
                    attachListener.bookmarkRestaurant(restaurant, adapterPosition)
                }
            }
        }

    }


}