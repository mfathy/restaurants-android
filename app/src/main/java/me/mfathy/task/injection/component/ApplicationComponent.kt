package me.mfathy.task.injection.component

import me.mfathy.task.BingeApp
import me.mfathy.task.injection.module.*
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import me.mfathy.task.injection.module.*
import javax.inject.Singleton

/**
 * Dagger application components
 */
@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ApplicationModule::class,
        DataModule::class,
        RemoteModule::class,
        CacheModule::class,
        UiModule::class,
        ViewModelsModule::class
    ]
)

interface ApplicationComponent : AndroidInjector<BingeApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<BingeApp>()

}