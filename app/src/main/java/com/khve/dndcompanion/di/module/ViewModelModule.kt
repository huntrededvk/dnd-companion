package com.khve.dndcompanion.di.module

import androidx.lifecycle.ViewModel
import com.khve.dndcompanion.di.key.ViewModelKey
import com.khve.dndcompanion.presentation.MainFragmentViewModel
import com.khve.dndcompanion.presentation.MainViewModel
import com.khve.dndcompanion.presentation.auth.SignUpViewModel
import com.khve.dndcompanion.presentation.auth.SignInViewModel
import com.khve.dndcompanion.presentation.meta.MetaListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(MetaListViewModel::class)
    @Binds
    fun bindMetaViewModel(viewModel: MetaListViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    @Binds
    fun bindSignInViewModel(viewModel: SignInViewModel): ViewModel

    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    @Binds
    fun bindSignUpViewModel(viewModel: SignUpViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainFragmentViewModel::class)
    @Binds
    fun bindMainFragmentViewModel(viewModel: MainFragmentViewModel): ViewModel
}