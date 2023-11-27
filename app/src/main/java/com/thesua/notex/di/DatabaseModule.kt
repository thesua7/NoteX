package com.thesua.notex.di

import android.content.Context
import androidx.room.Room
import com.thesua.notex.db.AppDao
import com.thesua.notex.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "noteXdb")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun getAppDao(appDatabase: AppDatabase): AppDao {
        return appDatabase.getDao()
    }
}