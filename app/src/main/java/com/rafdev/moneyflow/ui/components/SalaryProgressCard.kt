package com.rafdev.moneyflow.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rafdev.moneyflow.ui.uikit.card.UIKitCard
import com.rafdev.moneyflow.ui.uikit.text.UIKitText
import com.rafdev.moneyflow.utils.formatMoney

@Composable
fun SalaryProgressCard(
    salary: Double,
    expenses: Double,
    modifier: Modifier = Modifier
) {
    val progress = (expenses / salary).coerceIn(0.0, 1.0)

    val progressColor = when {
        progress < 0.5 -> Color(0xFF2E7D32)     // Verde (bien)
        progress < 0.8 -> Color(0xFFF9A825)     // Amarillo (ojo)
        else -> Color(0xFFC62828)               // Rojo (alto gasto)
    }

    val animatedProgress by animateFloatAsState(
        targetValue = progress.toFloat(),
        label = "salary_progress"
    )

    UIKitCard{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ----- Header -----
            UIKitText(
                text = "Resumen del mes",
            )

            // ----- Salary + Expenses -----
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column {
                    UIKitText(
                        text = "Sueldo",
                        color = Color.Gray
                    )
                    UIKitText(
                        text = formatMoney(salary),
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    UIKitText(
                        text = "Gastos",
                        color = Color.Gray
                    )
                    UIKitText(
                        text = formatMoney(expenses),
                        color = progressColor
                    )
                }
            }

            // ----- Progress -----
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

                LinearProgressIndicator(
                    progress = animatedProgress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                        .clip(RoundedCornerShape(50)),
                    color = progressColor,
                    trackColor = Color.LightGray.copy(alpha = 0.4f)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    UIKitText(
                        text = "Disponible: ${formatMoney(salary - expenses)}",
                    )

                    UIKitText(
                        text = "${(progress * 100).toInt()}%",
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SalaryProgressCardPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SalaryProgressCard(
            salary = 350000.0,
            expenses = 185000.0
        )
    }

}