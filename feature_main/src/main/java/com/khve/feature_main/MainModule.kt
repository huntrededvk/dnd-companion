package com.khve.feature_main

import com.khve.feature_main.data.repository.MainRepositoryImpl
import com.khve.feature_main.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MainModule {

    @Singleton
    @Binds
    fun bindUserRepository(impl: MainRepositoryImpl): MainRepository

}
