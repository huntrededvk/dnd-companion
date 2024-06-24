package com.khve.dndcompanion.presentation

import android.app.Application
import com.khve.dndcompanion.di.DaggerApplicationComponent

class CompanionApplication : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this.applicationContext, this)
    }
}
