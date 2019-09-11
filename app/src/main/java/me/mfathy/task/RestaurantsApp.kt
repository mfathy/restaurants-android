package me.mfathy.task

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import me.mfathy.task.injection.component.DaggerApplicationComponent

class RestaurantsApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.builder().create(this)
}
