package com.khve.dndcompanion

import androidx.test.ext.junit.rules.activityScenarioRule
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.khve.dndcompanion.presentation.MainActivity
import com.khve.dndcompanion.screen.AuthScreen
import org.junit.Rule
import org.junit.Test

class AuthTest : TestCase() {

    @get:Rule
    val activityScenario = activityScenarioRule<MainActivity>()

    @Test
    fun testAuthenticationWithShortUsername() = run {
        step("Check initial UI elements") {
            AuthScreen {
                with(etDiscord) {
                    isVisible()
                    hasHint(R.string.discord)
                }
                with(etUsername) {
                    isVisible()
                    hasHint(R.string.username)
                }
                with(etPassword) {
                    isVisible()
                    hasHint(R.string.password)
                }
                with(etEmail) {
                    isVisible()
                    hasHint(R.string.email)
                }
                with(btnSignUp) {
                    isVisible()
                    isClickable()
                }
            }
        }
        step("Login with short username") {
            AuthScreen {
                etEmail.replaceText("heyhey@gmail.com")
                etUsername.replaceText("heyhey")
                etDiscord.replaceText("heyhey")
                etPassword.replaceText("heyhey")
                btnSignUp.click()
            }
        }
    }
}