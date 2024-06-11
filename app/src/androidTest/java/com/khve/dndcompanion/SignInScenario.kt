package com.khve.dndcompanion

import com.kaspersky.kaspresso.testcases.api.scenario.Scenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext
import com.khve.dndcompanion.screen.SignInScreen

class SignInScenario : Scenario() {
    override val steps: TestContext<Unit>.() -> Unit = {
        step("Check UI elements") {
            SignInScreen {
                with(tvForgotPassword) {
                    isVisible()
                    hasText(R.string.forgot_password)
                }
                with(tvTitle) {
                    isVisible()
                    hasText(R.string.sign_in)
                }
                with(tvSignUp) {
                    isVisible()
                    hasText(R.string.sign_up)
                }
                with(etEmail) {
                    isVisible()
                    hasHint(R.string.email)
                }
                with(etPassword) {
                    isVisible()
                    hasHint(R.string.password)
                }
                with(btnSignIn) {
                    isVisible()
                    isClickable()
                }
            }
        }
    }
}