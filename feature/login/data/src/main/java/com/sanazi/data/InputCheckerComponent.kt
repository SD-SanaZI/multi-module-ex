package com.sanazi.list.data

import com.sanazi.list.data.di.InputCheckerModule
import com.sanazi.dependencies.InputCheckerProvider
import dagger.Component

@Component(modules = [InputCheckerModule::class])
interface InputCheckerComponent: InputCheckerProvider {
    companion object{
        fun create(): InputCheckerComponent{
            return DaggerInputCheckerComponent.factory().create()
        }
    }

    @Component.Factory
    interface Factory {
        fun create(): InputCheckerComponent
    }
}