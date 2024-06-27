package com.khve.dndcompanion.auth

import androidx.test.ext.junit.rules.activityScenarioRule
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.khve.dndcompanion.MainFragmentScreen
import com.khve.dndcompanion.R
import com.khve.dndcompanion.auth.scenario.SignUpScenario
import com.khve.dndcompanion.auth.screen.SignInScreen
import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.presentation.main.MainActivity
import org.junit.Rule
import org.junit.Test

class AuthTest : TestCase() {

    @get:Rule
    val activityScenario = activityScenarioRule<MainActivity>()

    private val userForSuccessfulAuth = UserSignUpDto(
        email = "    test@gmail.com   ",
        password = "    111111",
        username = "    Test     ",
        discord = "    Test     "
    )

    @Test
    fun testSuccessfulUserCreateWithCorrectFields() = run {
        step("Check Sign In is opened") {
                SignInScreen {
                    tvTitle.hasText(R.string.sign_in)
                }
        }
        step("Open sign up, check all initial UI elements and Sign Up with provided values") {
            scenario(
                SignUpScenario(userForSuccessfulAuth)
            )
        }
        step("Check that main screen fragment is opened and click Sign Out") {
            MainFragmentScreen {
                btnSignOut {
                    isVisible()
                    isClickable()
                    click()
                }
            }
        }
        step("Check that after Sign Out opened Sign In") {
            SignInScreen {
                tvTitle {
                    isVisible()
                    hasText(R.string.sign_in)
                }
            }
        }
        step("Sign In with correct email and password") {
            SignInScreen {
                etEmail.replaceText(userForSuccessfulAuth.email)
                etPassword.replaceText(userForSuccessfulAuth.password)
                btnSignIn {
                    isVisible()
                    isClickable()
                    click()
                }
            }
        }
        step("Check that main screen fragment is opened and delete current user") {
            MainFragmentScreen {
                btnSignOut {
                    isVisible()
                    isClickable()
                }
            }
            Firebase.auth.currentUser?.delete()
        }
        step("Check that after user was deleted opened Sign In") {
            SignInScreen {
                tvTitle {
                    isVisible()
                    hasText(R.string.sign_in)
                }
            }
        }
    }
}