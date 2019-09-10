package me.mfathy.task.injection.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import me.mfathy.task.features.restaurants.RestaurantsViewModel
import me.mfathy.task.injection.factory.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

/**
 * Dagger module to provide ViewModel dependencies.
 */
@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(RestaurantsViewModel::class)
    abstract fun bindSearchViewModel(viewModel: RestaurantsViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)
