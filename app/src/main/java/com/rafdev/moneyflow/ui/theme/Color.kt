package com.rafdev.moneyflow.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

object Palette {

    val primary = Color(0xFF111827)

    val background = Color(0xFF111827)
    val Surface = Color(0xFFFFFFFF)

    val TextPrimary = Color(0xFF111827)
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