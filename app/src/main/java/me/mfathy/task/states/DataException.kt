package me.mfathy.task.states

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 *
 * Sealed class for exception handling related to UI.
 */
sealed class DataException {
    data class ValidationError(val errorCode: Int) : DataException()
    object NotFoundException : DataException()
    object NetworkException : DataException()
    object ConnectionException : DataException()
    object GeneralException : DataException()
}