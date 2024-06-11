package com.khve.dndcompanion.di.module

import com.khve.dndcompanion.data.MainRepositoryImpl
import com.khve.dndcompanion.di.scope.ApplicationScope
import com.khve.dndcompanion.domain.repository.MainRepository
import dagger.Binds
import dagger.Module

@Module
interface MainModule {

    @ApplicationScope
    @Binds
    fun bindUserRepository(impl: MainRepositoryImpl): MainRepository

}