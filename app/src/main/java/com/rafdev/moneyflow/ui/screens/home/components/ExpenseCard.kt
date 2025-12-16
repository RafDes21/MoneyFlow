package com.rafdev.moneyflow.ui.screens.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafdev.moneyflow.ui.components.TextNumber
import com.rafdev.moneyflow.ui.screens.home.SplitNumber
import com.rafdev.moneyflow.ui.theme.Gray
import com.rafdev.moneyflow.ui.theme.Palette
import com.rafdev.moneyflow.ui.theme.Primary
import com.rafdev.moneyflow.ui.uikit.text.UIKitText
import com.rafdev.moneyflow.utils.Constants

@Composable
fun ExpenseCardFixed(
    splitFixed: String,
    modifier: Modifier = Modifier,
    activeBottomSheet: () -> Unit,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier
            .padding(vertical = 8.dp)
            .height(60.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Gray
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            UIKitText(
              text = splitFixed
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = activeBottomSheet) {
                Icon(
                    imageVector = Icons.Default.Add,
                    tint = Primary,
                    contentDescription = Constants.ShortTexts.DETAILS
                )
            }
            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.Info,
                    tint = Primary,
                    contentDescription = Constants.ShortTexts.DETAILS
                )
            }
        }
    }
}

