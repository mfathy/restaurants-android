package me.mfathy.task.data.store.memory

import androidx.collection.LruCache
import io.reactivex.Completable
import io.reactivex.Single
import me.mfathy.task.data.model.Restaurant

/**
 * Memory data store implementation.
 */
open class MemoryDataStore : MemoryCache {

    private val memoryCaching: LruCache<String, Any> = LruCache(50)

    override fun bookmark(entity: Restaurant): Completable {
        return Completable.defer {
            memoryCaching.put(entity.name, entity)
            Completable.complete()
        }
    }

    override fun unBookmark(entity: Restaurant): Completable {
        return Completable.defer {
            memoryCaching.remove(entity.name)
            Completable.complete()
        }
    }

    override fun getFavorites(): Single<List<Restaurant>> {
        return Single.defer {
            /*Filter only MovieEntity instances from memory*/
            Single.just(
                memoryCaching.snapshot().values
                    .filterIsInstance<Restaurant>()
            )

        }
    }

    override fun destroy() {
        memoryCaching.evictAll()
    }
}
