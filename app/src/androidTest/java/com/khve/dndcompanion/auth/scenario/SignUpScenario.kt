package com.khve.dndcompanion.auth.scenario

import com.kaspersky.kaspresso.testcases.api.scenario.Scenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.khve.dndcompanion.R
import com.khve.dndcompanion.auth.screen.SignInScreen
import com.khve.dndcompanion.auth.screen.SignUpScreen
import com.khve.dndcompanion.data.auth.model.UserSignUpDto

class SignUpScenario(
    private val user: UserSignUpDto
) : Scenario() {

    override val steps: TestContext<Unit>.() -> Unit = {
        step("Go to Sign Up from Sign In") {
            SignInScreen {
                tvSignUp {
                    isVisible()
                    isClickable()
                    click()
                }
            }
        }
        step("Check all initial UI elements") {
            SignUpScreen {
                ivBackground {
                    isVisible()
                }
                tvTitle {
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
                etUsername {
                    isVisible()
                    // hasHint(R.string.username_field)
                }
                etDiscord {
                    isVisible()
                    // hasHint(R.string.discord_field)
                }
                btnSignUp {
                    isVisible()
                    isClickable()
                }
            }
        }
        step("Enter provided data into fields") {
            SignUpScreen {
                etEmail.replaceText(user.email)
                etPassword.replaceText(user.password)
                etUsername.replaceText(user.username)
                etDiscord.replaceText(user.discord)
                btnSignUp.click()
            }
        }
    }
}