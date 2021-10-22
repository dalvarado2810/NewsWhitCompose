package com.example.newswhitcompose.di

import com.example.newswhitcompose.provider.NewsProvider
import com.example.newswhitcompose.repository.NewRepository
import com.example.newswhitcompose.repository.NewRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providerNewsRepository(provider: NewsProvider): NewRepository =
        NewRepositoryImp(provider)

}