package com.rafdev.moneyflow.ui.screens.events

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import kotlin.math.absoluteValue

@Composable
fun EventsScreen() {
    EventPagerScreen()
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EventPagerScreen() {


    var compactMode by remember { mutableStateOf(false) }

    val cardHeight by animateDpAsState(
        targetValue = if (compactMode) 180.dp else 390.dp,
        animationSpec = tween(200, easing = FastOutSlowInEasing),
        label = "cardHeight"
    )

    val pagerState = rememberPagerState(
        pageCount = { fakeEvents.size }
    )

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 64.dp),
        modifier = Modifier.fillMaxSize()
    ) { page ->

        val event = fakeEvents[page]
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateContentSize()
                    .align(
                        if (compactMode) Alignment.TopCenter
                        else Alignment.Center
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                // 🔝 CARD (siempre visible)
                EventCard(
                    event = event,
                    cardHeight = cardHeight,
                    pagerState = pagerState,
                    page = page,
                    compactMode = compactMode,
                    onClick = {
                        compactMode = !compactMode
                    }
                )

                AnimatedVisibility(
                    visible = compactMode,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    EventDetails(event)
                }
            }
        }
    }
}


@Composable
fun EventCard(
    event: Event,
    pagerState: PagerState,
    cardHeight: Dp,
    page: Int,
    compactMode: Boolean,
    onClick: () -> Unit
) {
    val pageOffset =
        (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction

    val scale =
        if (!compactMode) {
            lerp(
                start = 0.85f,
                stop = 1f,
                fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
            )
        } else 1f

    Card(
        modifier = Modifier
            .width(280.dp)
            .height(cardHeight) // 🔥 TODOS CAMBIAN
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clickable { onClick() }
            .animateContentSize(),
        shape = RoundedCornerShape(20.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(event.title, style = MaterialTheme.typography.titleMedium)
            Text(event.date, style = MaterialTheme.typography.bodySmall)
            Text(
                "$${event.total}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun EventDetails(event: Event) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateContentSize()
    ) {
        Text(
            text = "Gastos del evento",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(8.dp))
        LazyColumn {
            event.expenses.forEach { expense ->
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(expense.name)
                        Text("$${expense.amount}")
                    }
                }

            }
        }

    }
}

val fakeEvents = listOf(
    Event(
        id = 1,
        title = "Cumpleaños",
        date = "12 Mar 2026",
        total = 150.0,
        expenses = listOf(
            Expense("Torta", 40.0),
            Expense("Bebidas", 35.0),
            Expense("Decoración", 25.0),
            Expense("Torta", 40.0),
            Expense("Bebidas", 35.0),
            Expense("Decoración", 25.0),
        )
    ),
    Event(
        id = 2,
        title = "Viaje",
        date = "20 Abr 2026",
        total = 420.0,
        expenses = listOf(
            Expense("Hotel", 200.0),
            Expense("Comida", 120.0),
            Expense("Transporte", 100.0)
        )
    ),
    Event(
        id = 3,
        title = "Asado",
        date = "5 May 2026",
        total = 90.0,
        expenses = listOf(
            Expense("Carne", 55.0),
            Expense("Bebidas", 20.0),
            Expense("Pan", 15.0)
        )
    )
)

data class Event(
    val id: Int,
    val title: String,
    val date: String,
    val total: Double,
    val expenses: List<Expense>
)

data class Expense(
    val name: String,
    val amount: Double
)



