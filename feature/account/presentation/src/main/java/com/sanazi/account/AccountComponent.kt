package com.sanazi.account

import com.sanazi.dependencies.CoursesManagerProvider
import dagger.Component

@Component(
    dependencies = [CoursesManagerProvider::class]
)
interface AccountComponent {
    fun inject(accountFragment: AccountFragment)

    companion object {
        fun create(coursesManagerProvider: CoursesManagerProvider): AccountComponent {
            return DaggerAccountComponent.factory().create(coursesManagerProvider)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            coursesManagerProvider: CoursesManagerProvider
        ): AccountComponent
    }
}