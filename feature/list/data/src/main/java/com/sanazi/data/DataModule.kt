package com.sanazi.list.data

import android.content.Context
import com.sanazi.list.data.database.DatabaseBuilder
import com.sanazi.list.data.database.LikeRoomDatabase
import com.sanazi.list.data.database.UserLocalDataSource
import com.sanazi.list.data.database.UserLocalDataSourceImpl
import com.sanazi.list.data.net.UserRemoteDataSource
import com.sanazi.list.data.net.UserRemoteDataSourceImpl
import com.sanazi.list.domain.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module(
    includes = [DataModule.Inner::class]
)
class DataModule{
    @Provides
    fun provideDb(context: Context): LikeRoomDatabase =
        DatabaseBuilder.getInstance(context)

    @Module
    interface Inner{
        @Binds
        fun  provideLocal(userLocalDataSourceImpl: UserLocalDataSourceImpl): UserLocalDataSource

        @Binds
        fun  provideNet(userRemoteDataSourceImpl: UserRemoteDataSourceImpl): UserRemoteDataSource

        @Binds
        fun provideRepo(userRepositoryImpl: UserRepositoryImpl): UserRepository
    }
}