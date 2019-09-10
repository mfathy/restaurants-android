package me.mfathy.task.features.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_restaurants.*

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Base activity for common methods.
 */
open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    protected fun updateViewVisibility(
        showData: Boolean = false,
        showError: Boolean = false,
        showLoading: Boolean = false
    ) {

        swipe_refresh.visibility = if (showData) View.VISIBLE else View.INVISIBLE
        swipe_refresh.isRefreshing = showLoading

        recyclerView_List.visibility = if (showData) View.VISIBLE else View.INVISIBLE

        textView_List_callToAction.visibility = if (showError) View.VISIBLE else View.INVISIBLE
        progressBar_movieList_loading.visibility = if (showLoading) View.VISIBLE else View.INVISIBLE
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}