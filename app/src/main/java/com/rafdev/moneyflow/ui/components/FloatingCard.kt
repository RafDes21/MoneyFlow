package com.rafdev.moneyflow.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rafdev.moneyflow.ui.theme.PrimaryVariant
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@Composable
fun FloatingCard(
    text: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryVariant.copy(alpha = 0.3f)
        )
    ) {
        UIKitText(
            text = text,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)

        )
    }
}
