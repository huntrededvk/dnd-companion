package com.khve.dndcompanion.screen

import com.kaspersky.kaspresso.screens.KScreen
import com.khve.dndcompanion.R
import io.github.kakaocup.kakao.text.KButton

object MainFragmentScreen : KScreen<MainFragmentScreen>() {
    override val layoutId: Int? = null
    override val viewClass: Class<*>? = null

    val btnSignOut = KButton { withId(R.id.btn_sign_out) }
}