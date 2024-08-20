package com.khve.feature_dnd

import com.khve.feature_dnd.data.repository.DndRepositoryImpl
import com.khve.feature_dnd.domain.repository.DndRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DndModule {
    @Singleton
    @Binds
    fun bindDndRepository(impl: DndRepositoryImpl): DndRepository
}
