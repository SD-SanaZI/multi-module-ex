package com.sanazi.list.data

import com.sanazi.favoritedb.LikeRoomDatabaseModule
import com.sanazi.favoritedb.UserLocalDataSource
import com.sanazi.favoritedb.UserLocalDataSourceImpl
import com.sanazi.list.domain.CoursesRepository
import com.sanazi.network.UserRemoteDataSource
import com.sanazi.network.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module(
    includes = [DataModule.Inner::class, LikeRoomDatabaseModule::class]
)
class DataModule{
    @Module
    interface Inner{
        @Binds
        fun  bindLocal(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

        @Binds
        fun  bindNet(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

        @Binds
        fun bindRepo(userRepositoryImpl: CoursesRepositoryImpl): CoursesRepository
    }
}