package com.khve.dndcompanion.di

import android.app.Application
import android.content.Context
import com.khve.dndcompanion.presentation.meta.AddMetaItemFragment
import com.khve.dndcompanion.presentation.meta.MetaItemFragment
import com.khve.dndcompanion.di.module.AuthModule
import com.khve.dndcompanion.di.module.MainModule
import com.khve.dndcompanion.di.module.MetaModule
import com.khve.dndcompanion.di.module.ViewModelModule
import com.khve.dndcompanion.di.scope.ApplicationScope
import com.khve.dndcompanion.presentation.main.MainActivity
import com.khve.dndcompanion.presentation.main.MainFragment
import com.khve.dndcompanion.presentation.ViewModelFactory
import com.khve.dndcompanion.presentation.auth.SignInFragment
import com.khve.dndcompanion.presentation.auth.SignUpFragment
import com.khve.dndcompanion.presentation.meta.MetaListFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        MainModule::class,
        AuthModule::class,
        MetaModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory
    fun inject(fragment: SignUpFragment)
    fun inject(fragment: SignInFragment)
    fun inject(fragment: MetaListFragment)
    fun inject(fragment: MetaItemFragment)
    fun inject(fragment: AddMetaItemFragment)
    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}