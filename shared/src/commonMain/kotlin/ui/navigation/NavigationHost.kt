package ui.navigation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import core.NavigationDestination
import core.koinViewModel
import org.koin.compose.koinInject
import ui.notes.create.CreateNoteScreen
import ui.notes.overview.OverviewScreen

@Composable
fun NavigationHost(
    navigator: Navigator = koinInject()
) {
    val backStack: List<Navigator.Routes> by navigator.backStack.collectAsState()
    val previousBackStackSize: Int by remember { mutableStateOf(backStack.size) }
    val isForwardNavigation: Boolean by derivedStateOf { backStack.size > previousBackStackSize }

    AnimatedContent(
        targetState = backStack.lastOrNull(),
        modifier = Modifier.fillMaxSize(),
        transitionSpec = {
            if (isForwardNavigation) {
                slideInHorizontally(initialOffsetX = { width -> width }) togetherWith
                        slideOutHorizontally(targetOffsetX = { width -> -width })
            } else {
                slideInHorizontally(initialOffsetX = { width -> -width }) togetherWith
                        slideOutHorizontally(targetOffsetX = { width -> width })
            }
        }
    ) { route: Navigator.Routes? ->
        when (route) {
            Navigator.Routes.OVERVIEW_NOTES -> NavigationDestination { OverviewScreen(navigator) }
            Navigator.Routes.CREATE_NOTES -> NavigationDestination { CreateNoteScreen(navigator) }
            null -> Unit
        }
    }
}
