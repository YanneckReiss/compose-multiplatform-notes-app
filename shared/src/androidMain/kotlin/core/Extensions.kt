package core

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rickclephas.kmm.viewmodel.KMMViewModel
import org.koin.compose.getKoin
import java.util.UUID

// Workaround for handling view models based on the lifecycle
// of a composable; should probably not be used in production
@Composable
actual inline fun <reified T : KMMViewModel> koinViewModel(): T {

    val viewModelsProvider: ViewModelStoreViewModel = viewModel()
    val koin = getKoin()
    val destinationId: String = LocalDestinationId.current

    // If not we create a new one using the factory from koin
    // The created view model is then stored in the map inside viewModelsProvider
    // This is a workaround because our view-models are not actually cleared when navigating back^
    // This way we can force them two on dispose of our composable
    val viewModel: T = remember {
        viewModelsProvider.getViewModel(destinationId, T::class.java) ?: run {
            val createdViewModel: T = koin.get()
            viewModelsProvider.putViewModel(destinationId, createdViewModel)
            createdViewModel
        }
    }

    return viewModel
}

@Composable
actual fun DisposeNavigationDestinationEffect() {

    val activity = LocalContext.current as Activity
    val destinationId: String = LocalDestinationId.current
    val viewModelsProvider: ViewModelStoreViewModel = viewModel()

    DisposableEffect(Unit) {
        onDispose {
            if (!activity.isChangingConfigurations) {
                viewModelsProvider.clearDestination(destinationId)
            }
        }
    }
}

actual fun generateUUID() = UUID.randomUUID().toString()
