package com.khve.dndcompanion.di.module

import com.khve.dndcompanion.data.meta.repository.MetaListRepositoryImpl
import com.khve.dndcompanion.di.scope.ApplicationScope
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import dagger.Binds
import dagger.Module

@Module
interface MetaModule {

    @ApplicationScope
    @Binds
    fun bindMetaListRepository(impl: MetaListRepositoryImpl): MetaListRepository
}