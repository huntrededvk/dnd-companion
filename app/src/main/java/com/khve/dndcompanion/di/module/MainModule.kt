package com.khve.dndcompanion.di.module

import com.khve.dndcompanion.data.main.repository.MainRepositoryImpl
import com.khve.dndcompanion.domain.main.repository.MainRepository
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
