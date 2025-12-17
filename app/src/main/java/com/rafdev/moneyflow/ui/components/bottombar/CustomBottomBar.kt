package com.rafdev.moneyflow.ui.components.bottombar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rafdev.moneyflow.ui.navigation.Home
import com.rafdev.moneyflow.ui.navigation.bottomNavItems
import com.rafdev.moneyflow.ui.theme.Background
import com.rafdev.moneyflow.ui.theme.Gray
import com.rafdev.moneyflow.ui.theme.Primary
import com.rafdev.moneyflow.ui.theme.Surface
import com.rafdev.moneyflow.ui.theme.SurfaceAlt
import com.rafdev.moneyflow.ui.theme.TextMuted

@Composable
fun CustomBottomBar(navController: NavController) {
    val screens = bottomNavItems
    val currentRoute =
        navController.currentBackStackEntryAsState().value?.destination?.route

    Row(
        modifier = Modifier
            .background(SurfaceAlt)
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { item ->
            val isSelected = currentRoute == item.route::class.qualifiedName

            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp, bottom = 8.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        navController.navigate(item.route) {
                            popUpTo(Home) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier.size(22.dp),
                        painter = painterResource(id = item.icon),
                        contentDescription = stringResource(id = item.label),
                        tint = if (isSelected)
                            Primary
                        else
                            TextMuted

                    )
                    Text(
                        text = stringResource(id = item.label),
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        color = if (isSelected)
                            Primary
                        else
                            TextMuted
                    )
                }
            }
        }
    }
}
