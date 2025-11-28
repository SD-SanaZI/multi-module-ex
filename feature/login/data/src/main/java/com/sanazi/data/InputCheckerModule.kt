package com.sanazi.list.data.di

import com.sanazi.list.data.InputCheckerImpl
import com.sanazi.login.domain.InputChecker
import dagger.Binds
import dagger.Module

@Module
interface InputCheckerModule{
    @Binds
    fun provideInputChecker(inputCheckerImpl: InputCheckerImpl): InputChecker
}

