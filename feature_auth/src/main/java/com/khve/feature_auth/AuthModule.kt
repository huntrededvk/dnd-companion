package com.khve.feature_auth

import com.khve.feature_auth.data.repository.SignInRepositoryImpl
import com.khve.feature_auth.data.repository.SingUpRepositoryImpl
import com.khve.feature_auth.domain.repository.SignInRepository
import com.khve.feature_auth.domain.repository.SingUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Singleton
    @Binds
    abstract fun bindSignUpRepository(impl: SingUpRepositoryImpl): SingUpRepository

    @Singleton
    @Binds
    abstract fun bindSignInRepository(impl: SignInRepositoryImpl): SignInRepository
}