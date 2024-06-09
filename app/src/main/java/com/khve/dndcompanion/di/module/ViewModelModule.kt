package com.khve.dndcompanion.di.module

import androidx.lifecycle.ViewModel
import com.khve.dndcompanion.di.key.ViewModelKey
import com.khve.dndcompanion.presentation.auth.AuthViewModel
import com.khve.dndcompanion.presentation.meta.MetaViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MetaViewModel::class)
    @Binds
    fun bindMetaViewModel(viewModel: MetaViewModel): ViewModel

    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    @Binds
    fun bindAuthViewModel(viewModel: AuthViewModel): ViewModel
}