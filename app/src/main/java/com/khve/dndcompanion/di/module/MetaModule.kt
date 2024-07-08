package com.khve.dndcompanion.di.module

import com.khve.dndcompanion.data.meta.repository.MetaListRepositoryImpl
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MetaModule {

    @Singleton
    @Binds
    fun bindMetaListRepository(impl: MetaListRepositoryImpl): MetaListRepository
}
