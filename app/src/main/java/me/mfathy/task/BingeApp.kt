package me.mfathy.task

import me.mfathy.task.injection.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class BingeApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerApplicationComponent.builder().create(this)
}
