package me.mfathy.task.extensions.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Mohammed Fathy.
 * dev.mfathy@gmail.com
 */

fun <T : Any> Single<T>.subscribeAndObserve(): Single<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.subscribeAndObserve(): Completable =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())