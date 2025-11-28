package com.sanazi.presentation

import com.sanazi.dependencies.AppDependenciesProvider
import com.sanazi.list.presentation.ListFragment
import dagger.Component

@Component(
    dependencies = [AppDependenciesProvider::class]
)
interface ListComponent{
    fun inject(listFragment: ListFragment)

    companion object{
        fun create(appDependenciesProvider: AppDependenciesProvider): ListComponent{
            return DaggerListComponent.factory().create(appDependenciesProvider)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            appDependenciesProvider: AppDependenciesProvider
        ): ListComponent
    }
}