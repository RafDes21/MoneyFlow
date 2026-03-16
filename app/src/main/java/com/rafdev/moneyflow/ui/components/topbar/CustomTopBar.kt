package com.rafdev.moneyflow.ui.components.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafdev.moneyflow.R
import com.rafdev.moneyflow.ui.theme.Background
import com.rafdev.moneyflow.ui.theme.SubtitleText
import com.rafdev.moneyflow.ui.theme.TextPrimary
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    showBackButton: Boolean,
    onBackClick: (() -> Unit)? = null
) {

    Row(
        modifier = Modifier
            .background(Background)
            .fillMaxWidth()
            .statusBarsPadding()
            .height(76.dp)
            .padding(horizontal = if (showBackButton) 0.dp else 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showBackButton && onBackClick != null) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = TextPrimary
                )
            }
        }
        UIKitText(
            text = title,
            style = SubtitleText,
            color = TextPrimary
        )
    }

}

@Preview
@Composable
fun CustomTopBarPreview(){
    CustomTopBar(
        "customBar",
        showBackButton = true,
        onBackClick = {}
    )
}

@Preview
@Composable
fun CustomTopBarFalsePreview(){
    CustomTopBar(
        "customBar",
        showBackButton = false,
        onBackClick = {}
    )
}