package com.khve.dndcompanion.di.module

import com.khve.dndcompanion.data.dnd.repository.DndRepositoryImpl
import com.khve.dndcompanion.domain.dnd.repository.DndRepository
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
