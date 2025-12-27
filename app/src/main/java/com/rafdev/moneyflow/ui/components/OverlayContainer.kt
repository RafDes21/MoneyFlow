package com.rafdev.moneyflow.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.zIndex
import com.rafdev.moneyflow.ui.theme.Background
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun OverlayContainer(
    onClose: () -> Unit,
    content: @Composable () -> Unit
) {
    val offsetX = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
            .offset { IntOffset(offsetX.value.roundToInt(), 0) }
            .pointerInput(Unit) {
                detectHorizontalDragGestures(
                    onDragEnd = {
                        if (offsetX.value > 300f) {
                            scope.launch { onClose() }
                        } else {
                            scope.launch { offsetX.animateTo(0f) }
                        }
                    },
                    onHorizontalDrag = { _, dragAmount ->
                        if (dragAmount > 0) {
                            scope.launch {
                                offsetX.snapTo(offsetX.value + dragAmount)
                            }
                        }
                    }
                )
            }
            .background(Background)
            .statusBarsPadding()
    ) {
        content()
    }
}
