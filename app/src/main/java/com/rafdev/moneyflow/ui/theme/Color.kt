package com.rafdev.moneyflow.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val Background = Color(0xFF121212)
val Surface    = Color(0xFF0B0B0B)
val SurfaceAlt = Color(0xFF000000)
val Primary        = Color(0xFF22C55E)
val PrimaryVariant = Color(0xFF16A34A)
val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFFA1A1AA)
val TextMuted     = Color(0xFF9CA3AF)
val Gray = Color(0xFF1E1E1E)

val CardColor            = Color(0xFF181818)
val CardFocused     = Color(0xFF1F1F1F)
val CardPressed     = Color(0xFF242424)
val CardBorder      = Color(0xFF262626)


object Palette {

    val primary = Color(0xFF111827)

    //val background = Color(0xFF111827)
    val Surface = Color(0xFFFFFFFF)

    //val TextPrimary = Color(0xFF111827)
    val TextSecondary = Color(0xFF6B7280)
    val TextTertiary = Color(0xFF9CA3AF)
    val TextOnDark = Color.White

    val IconPrimary = Color(0xFF374151)
    val IconSecondary = Color(0xFF6B7280)
    val IconOnDark = Color(0xFFF9FAFB)
    val IconAccent = Color(0xFF2563EB)

    val Black = Color(0xFF000000)

    val BackgroundColor = Color(0xFF0F1115)         // Fondo general (muy oscuro, elegante)
    val CardColor = Color(0xFF1C1F24)               // Fondo para tarjetas
    val CardTextColor = Color(0xFFBFC6D1)           // Texto dentro de tarjetas
    val TextColor = Color(0xFFE8EDF2)               // Texto principal
    val BoxBackground = Color(0xFF15191E)           // Fondo para bloques adicionales

    val BottomNavColor = Color(0xFF161A20)          // Fondo navegación inferior
    val ActiveIconColor = Color(0xFF4DB6AC)         // Verde azulado moderno (acento)
    val InactiveIconColor = Color(0xFF6E7583)       // Gris azulado suave (íconos inactivos)
    val ErrorColor = Color(0xFFFF6B6B)              // Rojo coral (errores, delete, etc.)
    val Highlight = Color(0xFF80DEEA)
}

object CardPalette {
    val Background = Palette.CardColor                // Fondo principal del card
    val Title = Palette.TextColor                     // Título o nombre del gasto
    val Description = Palette.CardTextColor           // Descripción (tono más suave)
    val TimeDate = Palette.CardTextColor              // Fecha y hora (neutro claro)
    val AmountPositive = Palette.ActiveIconColor      // Monto positivo (verde azulado)
    val AmountNegative = Palette.ErrorColor           // Monto negativo (rojo coral)
    val IconTint = Palette.Highlight                  // Íconos de editar/eliminar
}

val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2563EB),
    onPrimary = Color(0xFFFFFFFF),

    secondary = Color(0xFF64748B),
    onSecondary = Color(0xFFFFFFFF),

    tertiary = Color(0xFF22C55E),
    onTertiary = Color(0xFFFFFFFF),

    background = Color(0xFFF8FAFC),
    onBackground = Color(0xFF0F172A),

    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0F172A),

    error = Color(0xFFEF4444),
    onError = Color(0xFFFFFFFF)
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF60A5FA),
    onPrimary = Color(0xFF020617),

    secondary = Color(0xFF94A3B8),
    onSecondary = Color(0xFF020617),

    tertiary = Color(0xFF4ADE80),
    onTertiary = Color(0xFF020617),

    background = Color(0xFF020617),
    onBackground = Color(0xFFE5E7EB),

    surface = Color(0xFF020617),
    onSurface = Color(0xFFE5E7EB),

    error = Color(0xFFF87171),
    onError = Color(0xFF020617)
)

