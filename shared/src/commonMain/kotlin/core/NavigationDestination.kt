package core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun NavigationDestination(
    content: @Composable () -> Unit
) {

    val destinationId: String = rememberSaveable { generateUUID() }

    DisposeNavigationDestinationEffect()

    CompositionLocalProvider(LocalDestinationId provides destinationId) {
        content()
    }
}

val LocalDestinationId: ProvidableCompositionLocal<String> = staticCompositionLocalOf {
    generateUUID()
}
