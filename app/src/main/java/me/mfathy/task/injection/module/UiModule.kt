package me.mfathy.task.injection.module

import me.mfathy.task.features.search.SearchActivity
import me.mfathy.task.injection.scope.ViewScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Dagger module to provide UI and activities dependencies.
 */
@Module
abstract class UiModule {
    @ViewScope
    @ContributesAndroidInjector
    abstract fun contributesSearchActivity(): SearchActivity

}