package me.mfathy.task.states

import me.mfathy.task.data.model.Restaurant

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Sealed class for bookmark operations result handling.
 */
sealed class BookmarkResult {
    data class OnSuccess(val data: Pair<Int, Restaurant>) : BookmarkResult()
    object OnFailure : BookmarkResult()
}