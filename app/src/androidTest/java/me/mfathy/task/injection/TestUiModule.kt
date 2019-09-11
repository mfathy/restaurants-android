package me.mfathy.task.injection

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.mfathy.task.features.restaurants.RestaurantsActivity
import me.mfathy.task.injection.scope.ViewScope

/**
 * Dagger module to provide UI and activities dependencies.
 */
@Module
abstract class TestUiModule {

    @ViewScope
    @ContributesAndroidInjector
    abstract fun contributesRestaurantsActivity(): RestaurantsActivity

}