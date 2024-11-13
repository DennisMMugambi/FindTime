package com.dennismugambi.findtime.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import lightGrey
import primaryColor
import primaryDarkColor
import primaryLightColor
import secondaryColor
import secondaryDarkColor
import secondaryLightColor
import typography

private val DarkColorPalette = darkColorScheme(
    primary = primaryDarkColor,
    primaryContainer = primaryLightColor,
    secondary = secondaryDarkColor,
    secondaryContainer = secondaryLightColor,
    onPrimary = Color.White,
    background = lightGrey,
    onSurface = lightGrey
)

private val LightColorPalette = lightColorScheme(
    primary = primaryColor,
    primaryContainer = primaryLightColor,
    secondary = secondaryColor,
    secondaryContainer = secondaryLightColor,
    onPrimary = Color.Black,
    background = Color.White
)

@Composable
fun AppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {

    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        content = content
    )
}