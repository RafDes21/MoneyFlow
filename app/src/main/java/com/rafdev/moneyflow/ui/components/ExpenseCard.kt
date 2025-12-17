package com.rafdev.moneyflow.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rafdev.moneyflow.ui.theme.CardPalette
import com.rafdev.moneyflow.ui.theme.Primary
import com.rafdev.moneyflow.ui.uikit.card.UIKitCard
import com.rafdev.moneyflow.ui.uikit.text.UIKitText

@Composable
fun ExpenseCard(
    title: String,
    time: String,
    amount: String,
    date: String,
    onDetail: () -> Unit,
    onUpdate: () -> Unit,
    onDelete: () -> Unit
) {

    UIKitCard(
        onClick = onDetail
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                UIKitText(
                    text = title,
                    fontSize = 14.sp
                )

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onUpdate,
                        modifier = Modifier.size(18.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Actualizar",
                            tint = Primary,
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar",
                            tint = Primary,

                            )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
            UIKitText(
                text = amount,
                fontSize = 22.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))

            if (time.isNotEmpty() && date.isNotEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    UIKitText(
                        text = time,
                        fontSize = 12.sp
                    )
                    UIKitText(
                        text = date,
                        fontSize = 12.sp
                    )
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseCardPreview() {
    ExpenseCard(
        title = "Compra de supermercado",
        time = "14:30",
        amount = "$150.00",
        date = "14",
        onDetail = {},
        onUpdate = { },
        onDelete = { }
    )
}