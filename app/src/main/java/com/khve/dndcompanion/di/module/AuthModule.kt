package com.khve.dndcompanion.di.module

import com.khve.dndcompanion.data.auth.repository.SignInRepositoryImpl
import com.khve.dndcompanion.data.auth.repository.SingUpRepositoryImpl
import com.khve.dndcompanion.domain.auth.repository.SignInRepository
import com.khve.dndcompanion.domain.auth.repository.SingUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {

    @Singleton
    @Binds
    fun bindSignUpRepository(impl: SingUpRepositoryImpl): SingUpRepository

    @Singleton
    @Binds
    fun bindSignInRepository(impl: SignInRepositoryImpl): SignInRepository
}
