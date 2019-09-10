package me.mfathy.task.injection.module

import android.app.Application
import android.content.Context
import me.mfathy.task.BingeApp
import dagger.Binds
import dagger.Module


/**
 * Dagger application module to provide app mContext.
 */
@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(application: BingeApp): Context

    @Binds
    abstract fun bindApplication(application: BingeApp): Application

}
