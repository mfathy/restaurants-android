package me.mfathy.task.injection.scope

import javax.inject.Scope

/**
 * Created by Mohammed Fathy
 * dev.mfathy@gmail.com
 *
 * Dagger scope for views "Activity | Fragment"
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewScope