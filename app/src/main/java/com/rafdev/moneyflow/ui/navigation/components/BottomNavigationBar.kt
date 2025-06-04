package com.rafdev.moneyflow.ui.navigation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rafdev.moneyflow.ui.navigation.screen.Screen
import com.rafdev.moneyflow.ui.theme.Palette

@Composable
fun BottomNavigationBar(navController: NavController) {
    val screens = Screen.bottomNavScreens
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar(
        containerColor = Palette.BottomNavColor
    ) {
        screens.forEach { screen ->
            val isSelected = currentRoute == screen.route

            NavigationBarItem(
                icon = {
                    screen.icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = screen.title,
                            tint = if (isSelected) Palette.ActiveIconColor else Palette.InactiveIconColor
                        )
                    }
                },
                label = {
                    screen.title?.let {
                        Text(
                            text = it,
                            color = if (isSelected) Palette.ActiveIconColor else Palette.InactiveIconColor
                        )
                    }
                },
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Palette.ActiveIconColor,
                    unselectedIconColor = Palette.InactiveIconColor,
                    selectedTextColor = Palette.ActiveIconColor,
                    unselectedTextColor = Palette.InactiveIconColor,
                    indicatorColor = Palette.BottomNavColor
                )
            )
        }
    }
}
