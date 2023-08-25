package core

import androidx.compose.runtime.Composable
import com.rickclephas.kmm.viewmodel.KMMViewModel

@Composable
expect inline fun <reified T : KMMViewModel> koinViewModel(): T

@Composable
expect fun DisposeNavigationDestinationEffect()

expect fun generateUUID(): String