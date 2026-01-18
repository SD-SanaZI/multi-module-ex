package com.sanazi.account

import android.content.Context
import com.sanazi.dependencies.AccountCoursesManagerProvider
import dagger.BindsInstance
import dagger.Component

@Component(modules = [AccountModule::class])
interface AccountDataComponent: AccountCoursesManagerProvider {

    companion object{
        fun create(context: Context): AccountDataComponent{
            return DaggerAccountDataComponent.factory().create(context)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AccountDataComponent
    }
}