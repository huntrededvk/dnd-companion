package com.khve.dndcompanion

import androidx.test.ext.junit.rules.activityScenarioRule
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.khve.dndcompanion.presentation.MainActivity
import com.khve.dndcompanion.screen.MainFragmentScreen
import com.khve.dndcompanion.screen.SignInScreen
import org.junit.Rule
import org.junit.Test

class SignInTest : TestCase(
    kaspressoBuilder = Kaspresso.Builder.advanced()
) {

    @get:Rule
    val activityScenario = activityScenarioRule<MainActivity>()

    @Test
    fun successfulSignInWithCorrectEmailAndPassword() = before {
        Firebase.auth.signOut()
    }.after {  }
        .run {
            step("Check UI elements") {
                SignInScenario()
            }
            step("Sign in with correct Email and Password") {
                SignInScreen {
                    etEmail.replaceText("heyhey@gmail.com")
                    etPassword.replaceText("heyhey")
                    btnSignIn.click()
                }
            }
            step("Check that correct activity started after login") {
                MainFragmentScreen {
                    with(btnSignOut) {
                        isVisible()
                        isClickable()
                        click()
                    }
                }
            }
            step("Check that Sign In Fragment is open after click Sign Out") {
                SignInScreen {
                    tvTitle.isVisible()
                }
            }
        }
}