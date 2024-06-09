package com.khve.dndcompanion.di.module

import com.khve.dndcompanion.data.auth.repository.UserRepositoryImpl
import com.khve.dndcompanion.di.scope.ApplicationScope
import com.khve.dndcompanion.domain.auth.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
interface AuthModule {

        @ApplicationScope
        @Binds
        fun bindUserRepository(impl: UserRepositoryImpl): UserRepository
}