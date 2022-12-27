package com.example.aairastation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFA3A3A3),
    onSurface = Color(0xFF0C0C0C),
    primary = Color(0xFFFFAE22),
    onPrimary = Color(0xFFFFFFFF),
    primaryVariant = Color(0xFFFFF0C4),
    error = Color(0xFFFF4848),
    secondary = Grey700,
)


@Composable
fun AairaStationTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}