package me.mfathy.task.features.restaurants

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
    private var originalRestaurants: MutableList<Restaurant>,
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
                    originalRestaurants
                } else {
                    originalRestaurants.filter {
                        it.name.toLowerCase(Locale.getDefault())
                            .contains(charString.toLowerCase(Locale.getDefault()))
                    }.toMutableList()
                }

                val filterResults = FilterResults()
                filterResults.values = filteredRestaurants
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredRestaurants = filterResults.values as MutableList<Restaurant>

                // refresh the list with filtered data
                val restaurantDiffUtils =
                    RestaurantDiffUtils(originalRestaurants, filteredRestaurants)
                val result = DiffUtil.calculateDiff(restaurantDiffUtils)
                result.dispatchUpdatesTo(this@RestaurantsAdapter)
            }
        }
    }

    fun updateRestaurants(list: List<Restaurant>) {
        val restaurantDiffUtils = RestaurantDiffUtils(list.toMutableList(), filteredRestaurants)
        val diffResult = DiffUtil.calculateDiff(restaurantDiffUtils)

        resetRestaurants()
        this.originalRestaurants.addAll(list)
        this.filteredRestaurants.addAll(list)

        diffResult.dispatchUpdatesTo(this)
    }

    fun updateRestaurant(restaurantPair: Pair<Int, Restaurant>) {
        filteredRestaurants[restaurantPair.first] = restaurantPair.second
        notifyItemChanged(restaurantPair.first)
    }

    private fun resetRestaurants() {
        this.originalRestaurants.clear()
        this.filteredRestaurants.clear()
    }

    fun sortItems(comparator: Comparator<Restaurant>) {
        filteredRestaurants.sortWith(comparator)

        val restaurantDiffUtils = RestaurantDiffUtils(originalRestaurants, filteredRestaurants)
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
            restaurant: Restaurant,
            attachListener: OnAttachRestaurantsListener
        ) {


            holder.textViewName?.text = restaurant.name
            holder.textViewOpenStatue?.text = restaurant.status

            holder.imageViewFavoriteBtn?.let {

                updateFavoriteIcon(mContext, restaurant.isFavorite, it)

                it.setOnClickListener {
                    restaurant.isFavorite = !restaurant.isFavorite
                    attachListener.bookmarkRestaurant(restaurant, adapterPosition)
                }
            }
        }

        private fun updateFavoriteIcon(
            context: Context,
            isFavorite: Boolean,
            imageView: ImageView
        ) {
            val removeDrawable = ContextCompat.getDrawable(
                context,
                R.drawable.ic_favorite_remove
            )
            val addDrawable = ContextCompat.getDrawable(
                context,
                R.drawable.ic_favorite_add
            )

            if (isFavorite) imageView.setImageDrawable(removeDrawable)
            else imageView.setImageDrawable(addDrawable)
        }

    }

}