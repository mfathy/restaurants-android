package me.mfathy.task.interactors.base

import io.reactivex.Completable
import io.reactivex.CompletableObserver

/**
 * Created by Mohammed Fathy
 * dev.mfathy@gmail.com
 *
 * CompletableUseCase provides a Completable observable to complete or error.
 */
abstract class CompletableUseCase<in Params> {

    abstract fun buildUseCaseCompletable(params: Params): Completable

    fun execute(observer: CompletableObserver, params: Params): CompletableObserver {
        return this.buildUseCaseCompletable(params)
            .subscribeWith(observer)
    }
}