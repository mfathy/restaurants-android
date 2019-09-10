package me.mfathy.task.interactors.base

import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

/**
 * Created by Mohammed Fathy
 * dev.mfathy@gmail.com
 *
 * SingleUseCase is an abstract class which provide a Single observable to emit required data or error.
 */
abstract class SingleUseCase<T> {

    abstract fun buildUseCaseSingle(): Single<T>

    fun execute(observer: DisposableSingleObserver<T>): DisposableSingleObserver<T> {
        return this.buildUseCaseSingle()
            .subscribeWith(observer)
    }
}