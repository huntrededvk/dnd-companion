package com.khve.dndcompanion.screen

import com.kaspersky.kaspresso.screens.KScreen
import com.khve.dndcompanion.R
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.image.KImageView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView
import org.junit.Test

object SignInScreen: KScreen<SignInScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val ivBackground = KImageView { withId(R.id.iv_background) }
    val tvTitle = KTextView { withId(R.id.tv_title) }
    val etEmail = KEditText { withId(R.id.et_email) }
    val etPassword = KEditText { withId(R.id.et_password) }
    val tvForgotPassword = KTextView { withId(R.id.tv_forgot_password) }
    val tvSignUp = KTextView { withId(R.id.tv_sign_up) }
    val btnSignIn = KButton { withId(R.id.btn_sign_in) }

}