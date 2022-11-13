package com.linku.im.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf

data class Duration(
    val immediately: Int = 0,
    val fast: Int = 200,
    val medium: Int = 600,
    val slow: Int = 800,
    val extraSlow: Int = 1200
)

val LocalDuration = staticCompositionLocalOf<Duration> { error("no duration provided") }
