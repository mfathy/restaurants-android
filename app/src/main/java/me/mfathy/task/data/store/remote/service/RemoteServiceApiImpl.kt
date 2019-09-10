package me.mfathy.task.data.store.remote.service

import android.content.Context
import com.google.gson.Gson
import io.reactivex.Single
import me.mfathy.task.data.store.remote.models.RestaurantsResponse
import java.io.IOException
import java.nio.charset.Charset
import javax.inject.Inject

class RemoteServiceApiImpl @Inject constructor(
    private val mContext: Context,
    private val mGson: Gson
) : RemoteServiceApi {
    override fun getRestaurants(): Single<RestaurantsResponse> {
        val response = loadJSONFromAssets(mContext, "json/response.json") ?: "{}"
        return Single.defer {
            Single.just(mGson.fromJson(response, RestaurantsResponse::class.java))
        }
    }

    private fun loadJSONFromAssets(context: Context, filePath: String): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open(filePath)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return json
    }
}