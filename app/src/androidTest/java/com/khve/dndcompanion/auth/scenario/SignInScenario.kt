package com.khve.dndcompanion.auth.scenario

import com.kaspersky.kaspresso.testcases.api.scenario.Scenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.khve.dndcompanion.R
import com.khve.dndcompanion.auth.screen.SignInScreen

class SignInScenario(
    private val email: String,
    private val password: String
) : Scenario() {
    override val steps: TestContext<Unit>.() -> Unit = {
        step("Check UI elements") {
            SignInScreen {
                tvForgotPassword {
                    isVisible()
                    hasText(R.string.forgot_password)
                }
                tvTitle {
                    isVisible()
                    hasText(R.string.sign_in)
                }
                tvSignUp {
                    isVisible()
                    hasText(R.string.sign_up)
                }
                etEmail {
                    isVisible()
                    // hasHint(R.string.email_field)
                }
                etPassword {
                    isVisible()
                    // hasHint(R.string.password_field)
                }
                btnSignIn {
                    isVisible()
                    isClickable()
                }
            }
        }
        step("Enter email, password and click Sign In") {
            SignInScreen {
                etEmail.replaceText(email)
                etPassword.replaceText(password)
                btnSignIn.click()
            }
        }
    }
}