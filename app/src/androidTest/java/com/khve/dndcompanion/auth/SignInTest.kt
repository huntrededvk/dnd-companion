package com.khve.dndcompanion.auth

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.khve.dndcompanion.auth.scenario.SignInScenario
import com.khve.dndcompanion.auth.screen.SignInScreen
import com.khve.dndcompanion.presentation.main.MainActivity
import org.junit.Rule
import org.junit.Test


class SignInTest : TestCase() {
    @get:Rule
    val activityScenario = activityScenarioRule<MainActivity>()

    @Test
    fun testUserSignInIncorrectEmailCorrectPassword() = run {
        SignInScreen {
            scenario(
                SignInScenario("email", "password")
            )
            
        }
    }
}