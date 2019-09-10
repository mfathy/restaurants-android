package me.mfathy.task.injection

import me.mfathy.task.features.favorites.FavoritesActivity
import me.mfathy.task.features.restaurants.RestaurantsActivity
import me.mfathy.task.injection.scope.ViewScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Dagger module to provide UI and activities dependencies.
 */
@Module
abstract class TestUiModule {

    @ViewScope
    @ContributesAndroidInjector
    abstract fun contributesSearchActivity(): RestaurantsActivity

    @ViewScope
    @ContributesAndroidInjector
    abstract fun contributesFavortiesActivity(): FavoritesActivity
}