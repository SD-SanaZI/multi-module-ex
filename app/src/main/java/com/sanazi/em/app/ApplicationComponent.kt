package com.sanazi.em.app

import android.content.Context
import com.sanazi.list.data.InputCheckerComponent
import com.sanazi.dependencies.AppDependenciesProvider
import com.sanazi.dependencies.InputCheckerProvider
import com.sanazi.dependencies.UserRepositoryProvider
import com.sanazi.list.data.di.DataComponent
import dagger.Component

@Component(
    dependencies = [
        UserRepositoryProvider::class,
        InputCheckerProvider::class
    ]
)
interface ApplicationComponent: AppDependenciesProvider {

    companion object{
        fun create(context: Context): ApplicationComponent{
            val userRepositoryProvider = DataComponent.create(context)
            val inputCheckerProvider = InputCheckerComponent.create()
            return DaggerApplicationComponent.factory().create(
                userRepositoryProvider, inputCheckerProvider
            )
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            userRepositoryProvider: UserRepositoryProvider,
            inputCheckerProvider: InputCheckerProvider
        ): ApplicationComponent
    }
}