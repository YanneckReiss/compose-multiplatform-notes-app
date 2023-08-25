package ui.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


@Composable
expect fun BackHandler(onBackPressed: () -> Unit)

@Composable
expect fun StatusBarColor(
    color: Color,
    lightStatusBar: Boolean
)
