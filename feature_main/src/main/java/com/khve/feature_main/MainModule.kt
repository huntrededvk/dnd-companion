package com.khve.feature_main

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
    fun bindUserRepository(impl: com.khve.feature_main.data.repository.MainRepositoryImpl): com.khve.feature_main.domain.repository.MainRepository

}
