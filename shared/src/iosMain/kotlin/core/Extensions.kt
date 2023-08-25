package core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.rickclephas.kmm.viewmodel.KMMViewModel
import org.koin.compose.koinInject
import platform.Foundation.NSUUID

@Composable
actual inline fun <reified T : KMMViewModel> koinViewModel(): T {

    val viewModel: T = koinInject()

    DisposableEffect(Unit) {
        onDispose {
            viewModel.onCleared()
        }
    }

    return viewModel
}

@Composable
actual fun DisposeNavigationDestinationEffect() {
    // Nothing to do here
}

actual fun generateUUID(): String = NSUUID().UUIDString()
