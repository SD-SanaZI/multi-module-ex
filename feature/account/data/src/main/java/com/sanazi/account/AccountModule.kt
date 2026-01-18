package com.sanazi.account

import com.sanazi.favoritedb.LikeRoomDatabaseModule
import com.sanazi.favoritedb.UserLocalDataSource
import com.sanazi.favoritedb.UserLocalDataSourceImpl
import com.sanazi.network.UserRemoteDataSource
import com.sanazi.network.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module(
    includes = [AccountModule.Inner::class, LikeRoomDatabaseModule::class]
)
class AccountModule{
    @Module
    interface Inner{
        @Binds
        fun  bindLocal(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

        @Binds
        fun  bindNet(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

        @Binds
        fun bindRepo(userRepositoryImpl: AccountCoursesRepositoryImpl): AccountCoursesRepository
    }
}