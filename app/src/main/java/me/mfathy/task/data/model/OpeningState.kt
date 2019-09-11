package me.mfathy.task.data.model

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Enum class for Restaurant Opening States.
 */
@Suppress("UNUSED_PARAMETER")
enum class OpeningState(state: Int) {
    OPEN(1), ORDER_AHEAD(2), CLOSED(3)
}