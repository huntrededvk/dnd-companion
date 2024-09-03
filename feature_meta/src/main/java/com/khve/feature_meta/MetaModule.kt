package com.khve.feature_meta

import com.khve.feature_meta.data.repository.MetaListRepositoryImpl
import com.khve.feature_meta.domain.repository.MetaListRepository
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
