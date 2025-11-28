package com.sanazi.dependencies

import com.sanazi.login.domain.InputChecker

interface InputCheckerProvider{
    fun provideInputChecker() : InputChecker
}