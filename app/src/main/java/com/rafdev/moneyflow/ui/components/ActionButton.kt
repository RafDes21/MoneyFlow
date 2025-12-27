package com.rafdev.moneyflow.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafdev.moneyflow.ui.model.IconPosition
import com.rafdev.moneyflow.ui.theme.OnPrimary
import com.rafdev.moneyflow.ui.theme.PrimaryVariant
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcon
import com.rafdev.moneyflow.ui.uikit.icon.UIKitIcons
import com.rafdev.moneyflow.ui.uikit.text.UIKitText


@Composable
fun ActionButton(
    text: String,
    modifier: Modifier = Modifier,
    @DrawableRes iconRes: Int? = null,
    iconPosition: IconPosition = IconPosition.LEFT,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PrimaryVariant,
            disabledContainerColor = PrimaryVariant.copy(alpha = 0.4f)
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (iconRes != null && iconPosition == IconPosition.LEFT) {
                UIKitIcon(
                    tint = OnPrimary,
                    iconRes = iconRes,
                    contentDescription = "",
                    size = 18.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            UIKitText(text = text.uppercase())

            if (iconRes != null && iconPosition == IconPosition.RIGHT) {
                Spacer(modifier = Modifier.width(8.dp))
                UIKitIcon(
                    tint = OnPrimary,
                    iconRes = iconRes,
                    contentDescription = "",
                    size = 18.dp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActionButtonPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Solo texto
        ActionButton(
            text = "Guardar",
            onClick = {}
        )

        // Icono a la izquierda
        ActionButton(
            text = "Agregar",
            iconRes = UIKitIcons.Add,
            onClick = {}
        )

        // Icono a la derecha
        ActionButton(
            text = "Continuar",
            iconRes = UIKitIcons.delete,
            iconPosition = IconPosition.RIGHT,
            onClick = {}
        )

        // Deshabilitado
        ActionButton(
            text = "Deshabilitado",
            iconRes = UIKitIcons.Add,
            enabled = false,
            onClick = {}
        )
    }
}

