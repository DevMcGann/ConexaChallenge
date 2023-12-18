package com.gsoft.conexachallenge.di

import android.app.Application
import com.gsoft.conexachallenge.data.datasource.local.ProductDao
import com.gsoft.conexachallenge.data.datasource.remote.ProductApi
import com.gsoft.conexachallenge.data.repository.ProductRepository
import com.gsoft.conexachallenge.domain.repository.ProductRepositoryImpl
import com.gsoft.conexachallenge.util.NetworkUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideApiRepository(
        api: ProductApi,
        dao: ProductDao,
        networkUtils: NetworkUtils
    ): ProductRepository {
        return ProductRepositoryImpl(
            api = api,
            dao = dao,
            networkUtils = networkUtils
        )
    }


    @Provides
    @Singleton
    fun provideNetworkUtils(application: Application): NetworkUtils {
        return NetworkUtils(application)
    }

}