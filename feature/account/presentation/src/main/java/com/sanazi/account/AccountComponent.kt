package com.sanazi.account

import com.sanazi.dependencies.AccountCoursesManagerProvider
import dagger.Component

@Component(
    dependencies = [AccountCoursesManagerProvider::class]
)
interface AccountComponent {
    fun inject(accountFragment: AccountFragment)

    companion object {
        fun create(accountCoursesManagerProvider: AccountCoursesManagerProvider): AccountComponent {
            return DaggerAccountComponent.factory().create(accountCoursesManagerProvider)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            accountCoursesManagerProvider: AccountCoursesManagerProvider
        ): AccountComponent
    }
}