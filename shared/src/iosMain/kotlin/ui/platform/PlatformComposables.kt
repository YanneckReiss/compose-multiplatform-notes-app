package ui.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
actual fun BackHandler(onBackPressed: () -> Unit) {
    // Nothing to do here, iOS has no back button to handle
}

@Composable
actual fun StatusBarColor(
    color: Color,
    lightStatusBar: Boolean
) {
    // We handle this part directly inside the iOS code
}
