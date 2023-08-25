package ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Shapes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.platform.StatusBarColor

// Fill out the Colors(..) constructor based on following colors:
// Background Color: #02142B
// Primary Color: #6C42BD
// Secondary Color: #C6F3FE

private val colorPrimary = Color(0xFF6C42BD)
private val colorBackground = Color(0xFF02142B)
private val colorSecondary = Color(0xFFC6F3FE)

private val colorsLight = Colors(
    primary = colorPrimary,
    primaryVariant = colorPrimary,
    secondary = colorSecondary,
    secondaryVariant = colorSecondary,
    background = colorBackground,
    surface = colorSecondary,
    error = Color(0xFFB00020),
    onPrimary = colorSecondary,
    onSecondary = colorBackground,
    onBackground = colorSecondary,
    onSurface = colorBackground,
    onError = Color(0xFFFF4545),
    isLight = true
)

private val shapes = Shapes(
    small = RoundedCornerShape(16.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(0.dp),
)

@Composable
fun AppTheme(
    content: @Composable () -> Unit
) {

    StatusBarColor(
        color = colorsLight.secondary,
        lightStatusBar = true
    )

    MaterialTheme(
        colors = colorsLight,
        shapes = shapes,
        content = content
    )
}
