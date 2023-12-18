package com.gsoft.conexachallenge.di

import android.app.Application
import androidx.room.Room
import com.gsoft.conexachallenge.data.datasource.local.AppDataBase
import com.gsoft.conexachallenge.data.datasource.local.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(application: Application): AppDataBase {
        return Room.databaseBuilder(application, AppDataBase::class.java, "ConexaChallengeDB")
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideDao(appDataBase: AppDataBase): ProductDao {
        return appDataBase.dao()
    }


}