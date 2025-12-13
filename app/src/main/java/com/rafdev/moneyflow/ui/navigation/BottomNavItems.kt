package com.rafdev.moneyflow.ui.navigation

import com.rafdev.moneyflow.R

data class BottomNavItem(
    val route: Any,
    val label: Int,
    val icon: Int
)

val bottomNavItems = listOf(
    BottomNavItem(Home, R.string.tab_home, R.drawable.ic_home),
    BottomNavItem(Cards, R.string.tab_cards, R.drawable.ic_credit_card),
)