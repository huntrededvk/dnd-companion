package com.khve.dndcompanion.di.module

import com.khve.dndcompanion.data.auth.repository.SignInRepositoryImpl
import com.khve.dndcompanion.data.auth.repository.SingUpRepositoryImpl
import com.khve.dndcompanion.di.scope.ApplicationScope
import com.khve.dndcompanion.domain.auth.repository.SignInRepository
import com.khve.dndcompanion.domain.auth.repository.SingUpRepository
import dagger.Binds
import dagger.Module

@Module
interface AuthModule {

    @ApplicationScope
    @Binds
    fun bindSignUpRepository(impl: SingUpRepositoryImpl): SingUpRepository

    @ApplicationScope
    @Binds
    fun bindSignInRepository(impl: SignInRepositoryImpl): SignInRepository
}
