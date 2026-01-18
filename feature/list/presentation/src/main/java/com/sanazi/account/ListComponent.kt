package com.sanazi.account

import com.sanazi.dependencies.CoursesManagerProvider
import com.sanazi.list.presentation.ListFragment
import dagger.Component

@Component(
    dependencies = [CoursesManagerProvider::class]
)
interface ListComponent{
    fun inject(listFragment: ListFragment)

    companion object{
        fun create(coursesManagerProvider: CoursesManagerProvider): ListComponent{
            return DaggerListComponent.factory().create(coursesManagerProvider)
        }
    }

    @Component.Factory
    interface Factory {
        fun create(
            coursesManagerProvider: CoursesManagerProvider
        ): ListComponent
    }
}