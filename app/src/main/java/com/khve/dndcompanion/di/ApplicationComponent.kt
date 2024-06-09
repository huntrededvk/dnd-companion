package com.khve.dndcompanion.di

import android.app.Application
import android.content.Context
import com.khve.dndcompanion.di.module.AuthModule
import com.khve.dndcompanion.di.module.MetaModule
import com.khve.dndcompanion.di.module.ViewModelModule
import com.khve.dndcompanion.di.scope.ApplicationScope
import com.khve.dndcompanion.presentation.ViewModelFactory
import com.khve.dndcompanion.presentation.auth.SignUpFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        AuthModule::class,
        MetaModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun getViewModelFactory(): ViewModelFactory
    fun inject(fragment: SignUpFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}