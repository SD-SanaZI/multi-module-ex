package com.sanazi.login.presentation

import dagger.Component
import com.sanazi.dependencies.AppDependenciesProvider

@Component(
    dependencies = [AppDependenciesProvider::class]
)
interface LoginComponent{
    fun inject(loginFragment: LoginFragment)

    companion object{
        fun create(appDependenciesProvider: AppDependenciesProvider): LoginComponent{
            return DaggerLoginComponent.factory().create(appDependenciesProvider)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            appDependenciesProvider: AppDependenciesProvider
        ): LoginComponent
    }
}