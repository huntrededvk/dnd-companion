package com.khve.dndcompanion.screen

import com.kaspersky.kaspresso.screens.KScreen
import com.khve.dndcompanion.R
import io.github.kakaocup.kakao.edit.KEditText

object AuthScreen: KScreen<AuthScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val etEmail = KEditText { withId(R.id.etEmail) }
    val etPassword = KEditText { withId(R.id.etPassword) }
    val etUsername = KEditText { withId(R.id.etUsername) }
    val etDiscord = KEditText { withId(R.id.etDiscord) }
    val btnSignUp = KEditText { withId(R.id.btnSignUp) }
}