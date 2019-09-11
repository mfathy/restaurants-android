package me.mfathy.task.injection.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import me.mfathy.task.RestaurantsApp


/**
 * Dagger application module to provide app mContext.
 */
@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(application: RestaurantsApp): Context

    @Binds
    abstract fun bindApplication(application: RestaurantsApp): Application

}
