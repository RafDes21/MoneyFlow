package com.rafdev.moneyflow.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.rafdev.moneyflow.ui.theme.CustomTypography
import com.rafdev.moneyflow.utils.Constants

@Composable
fun HomeScreen(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 20.dp, 10.dp, 0.dp)
    ) {
        Text(
            text = Constants.BUDGET,
            style = CustomTypography.titleLarge,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
        )

        Row(
            modifier = Modifier
                .align(alignment = Alignment.CenterHorizontally)
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$ 0.0",
                style = CustomTypography.titleLarge,
            )

            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(22.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = Constants.EDIT_BUDGET
                )
            }

        }

        Text(text = Constants.EXPENSES_SCHEDULED)

        ExpenseCard(text = "0.0")


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = Constants.ACTIVITIES,
            )

            IconButton(
                onClick = {
                    //navController.navigate("addActivityScreen")
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = Constants.ADD_ACTIVITY
                )
            }
        }

    }

}

@Composable
fun ExpenseCard(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(60.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
            )

            IconButton(onClick = onClick) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = Constants.DETAILS
                )
            }
        }
    }
}
