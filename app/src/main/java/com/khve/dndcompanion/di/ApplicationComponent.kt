package com.khve.dndcompanion.di

import com.khve.feature_auth.AuthModule
import com.khve.feature_auth.presentation.SignInFragment
import com.khve.feature_auth.presentation.SignUpFragment
import com.khve.feature_dnd.DndModule
import com.khve.feature_main.MainModule
import com.khve.feature_main.presentation.MainActivity
import com.khve.feature_main.presentation.MainFragment
import com.khve.feature_meta.MetaModule
import com.khve.feature_meta.presentation.AddMetaItemFragment
import com.khve.feature_meta.presentation.MetaItemFragment
import com.khve.feature_meta.presentation.MetaListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AuthModule::class,
        AppModule::class,
        MainModule::class,
        MetaModule::class,
        DndModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: SignUpFragment)
    fun inject(fragment: SignInFragment)
    fun inject(fragment: MetaListFragment)
    fun inject(fragment: MetaItemFragment)
    fun inject(fragment: AddMetaItemFragment)
    fun inject(activity: MainActivity)
    fun inject(fragment: MainFragment)

}
