package com.sanazi.favoritedb

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class LikeRoomDatabaseModule{
    @Provides
    fun provideDb(context: Context): LikeRoomDatabase =
        DatabaseBuilder.getInstance(context)
}