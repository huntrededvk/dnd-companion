package com.khve.dndcompanion.auth

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.khve.dndcompanion.auth.scenario.SignInScenario
import com.khve.dndcompanion.auth.screen.SignInScreen
import org.junit.Rule
import org.junit.Test


class SignInTest : TestCase() {
    @get:Rule
    val activityScenario = activityScenarioRule<com.khve.feature_main.presentation.MainActivity>()

    @Test
    fun testUserSignInIncorrectEmailCorrectPassword() = run {
        SignInScreen {
            scenario(
                SignInScenario("email", "password")
            )
            
        }
    }
}