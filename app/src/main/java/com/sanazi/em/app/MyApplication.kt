package com.sanazi.em.app

import android.app.Application
import com.sanazi.dependencies.AppDependenciesProvider
import com.sanazi.dependencies.DependenciesCarrier

class MyApplication: Application(), DependenciesCarrier {
    private var appComponent: ApplicationComponent? = null

    override fun getAppDependenciesProvider(): AppDependenciesProvider {
        if (appComponent == null)
            appComponent = ApplicationComponent.create(applicationContext)
        return requireNotNull(appComponent)
    }
}