package com.sanazi.dependencies

import com.sanazi.account.GetAllCoursesUseCase
import com.sanazi.account.SetFavoriteUseCase

interface AccountCoursesManagerProvider{
    fun getAllAccountCoursesUseCase(): GetAllCoursesUseCase
    fun setAccountFavoriteUseCase(): SetFavoriteUseCase
}