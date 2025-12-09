package com.sanazi.em.app

import android.content.Context
import com.sanazi.list.data.InputCheckerComponent
import com.sanazi.dependencies.AppDependenciesProvider
import com.sanazi.dependencies.InputCheckerProvider
import com.sanazi.dependencies.CoursesManagerProvider
import com.sanazi.list.data.di.DataComponent
import dagger.Component

@Component(
    dependencies = [
        CoursesManagerProvider::class,
        InputCheckerProvider::class
    ]
)
interface ApplicationComponent: AppDependenciesProvider {

    companion object{
        fun create(context: Context): ApplicationComponent{
            val coursesManagerProvider = DataComponent.create(context)
            val inputCheckerProvider = InputCheckerComponent.create()
            return DaggerApplicationComponent.factory().create(
                coursesManagerProvider, inputCheckerProvider
            )
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            coursesManagerProvider: CoursesManagerProvider,
            inputCheckerProvider: InputCheckerProvider
        ): ApplicationComponent
    }
}