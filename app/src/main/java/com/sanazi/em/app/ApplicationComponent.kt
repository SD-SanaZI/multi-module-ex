package com.sanazi.em.app

import android.content.Context
import com.sanazi.account.AccountDataComponent
import com.sanazi.dependencies.AccountCoursesManagerProvider
import com.sanazi.dependencies.AppDependenciesProvider
import com.sanazi.dependencies.CoursesManagerProvider
import com.sanazi.dependencies.InputCheckerProvider
import com.sanazi.list.data.InputCheckerComponent
import com.sanazi.list.data.di.DataComponent
import dagger.Component

@Component(
    dependencies = [
        CoursesManagerProvider::class,
        InputCheckerProvider::class,
        AccountCoursesManagerProvider::class
    ]
)
interface ApplicationComponent: AppDependenciesProvider {

    companion object{
        fun create(context: Context): ApplicationComponent{
            val coursesManagerProvider = DataComponent.create(context)
            val inputCheckerProvider = InputCheckerComponent.create()
            val accountComponent = AccountDataComponent.create(context)
            return DaggerApplicationComponent.factory().create(
                coursesManagerProvider, inputCheckerProvider, accountComponent
            )
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            coursesManagerProvider: CoursesManagerProvider,
            inputCheckerProvider: InputCheckerProvider,
            accountCoursesManagerProvider: AccountCoursesManagerProvider
        ): ApplicationComponent
    }
}