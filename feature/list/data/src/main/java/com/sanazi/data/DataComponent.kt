package com.sanazi.list.data.di

import android.content.Context
import com.sanazi.dependencies.CoursesManagerProvider
import com.sanazi.list.data.DataModule
import dagger.BindsInstance
import dagger.Component

@Component(modules = [DataModule::class])
interface DataComponent: CoursesManagerProvider {

    companion object{
        fun create(context: Context): DataComponent{
            return DaggerDataComponent.factory().create(context)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DataComponent
    }
}