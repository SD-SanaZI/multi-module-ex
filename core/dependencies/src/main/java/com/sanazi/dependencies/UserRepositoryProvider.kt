package com.sanazi.dependencies

import com.sanazi.list.domain.UserRepository

interface UserRepositoryProvider{
    fun provideUserRepository() : UserRepository
}

