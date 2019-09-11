package me.mfathy.task.injection

import android.app.Application
import me.mfathy.task.TestApplication
import me.mfathy.task.data.repository.RestaurantsRepository
import me.mfathy.task.injection.module.ViewModelsModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidSupportInjectionModule::class,
        TestApplicationModule::class,
        TestDataModule::class,
        ViewModelsModule::class,
        TestUiModule::class,
        TestCacheModule::class,
        TestRemoteModule::class]
)
interface TestApplicationComponent {

    fun repository(): RestaurantsRepository

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): TestApplicationComponent
    }

    fun inject(application: TestApplication)

}