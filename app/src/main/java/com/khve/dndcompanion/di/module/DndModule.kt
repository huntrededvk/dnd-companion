package com.khve.dndcompanion.di.module

import com.khve.dndcompanion.data.dnd.repository.DndRepositoryImpl
import com.khve.dndcompanion.di.scope.ApplicationScope
import com.khve.dndcompanion.domain.dnd.repository.DndRepository
import dagger.Binds
import dagger.Module

@Module
interface DndModule {
    @ApplicationScope
    @Binds
    fun bindDndRepository(impl: DndRepositoryImpl): DndRepository
}