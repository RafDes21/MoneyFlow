package com.rafdev.moneyflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.rafdev.domain.model.Budget
import com.rafdev.moneyflow.ui.navigation.AppNavigation
import com.rafdev.moneyflow.ui.theme.MoneyFlowTheme
import dagger.hilt.android.AndroidEntryPoint

//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//
//    private val _isSplashVisible = mutableStateOf(true)
//    val isSplashVisible: State<Boolean> get() = _isSplashVisible
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MoneyFlowTheme {
//                val splashVisible = isSplashVisible.value
//
//                // Controlar visibilidad de barras según splashVisible
//                val windowInsetsController = remember {
//                    WindowInsetsControllerCompat(window, window.decorView)
//                }
//
//                LaunchedEffect(splashVisible) {
//                    if (splashVisible) {
//                        windowInsetsController.hide(
//                            WindowInsetsCompat.Type.statusBars() or
//                                    WindowInsetsCompat.Type.navigationBars()
//                        )
//                        windowInsetsController.systemBarsBehavior =
//                            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
//                    } else {
//                        windowInsetsController.show(
//                            WindowInsetsCompat.Type.statusBars() or
//                                    WindowInsetsCompat.Type.navigationBars()
//                        )
//                    }
//                }
//
//                AppNavigation(
//                    onSplashFinished = { _isSplashVisible.value = false }
//                )
//            }
//        }
//    }
//}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        WindowCompat.setDecorFitsSystemWindows(window, false)
//        val controller = WindowInsetsControllerCompat(window, window.decorView)
//        controller.hide(WindowInsetsCompat.Type.statusBars() or WindowInsetsCompat.Type.navigationBars())
//        controller.systemBarsBehavior =
//            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        setContent {
            MoneyFlowTheme {
                AppNavigation()
            }
        }
    }
}

