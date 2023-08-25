package ui.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Navigator {

    enum class Routes {
        OVERVIEW_NOTES,
        CREATE_NOTES
    }

    private val _backStack = MutableStateFlow(listOf(Routes.OVERVIEW_NOTES))
    val backStack: StateFlow<List<Routes>> = _backStack.asStateFlow()

    fun navigateTo(route: Routes) {
        if (_backStack.value.last() == route) return

        _backStack.update { currentState ->
            currentState + route
        }
    }

    fun navigateBack() {
        if (_backStack.value.size > 1) {
            _backStack.update { currentState ->
                currentState.dropLast(1)
            }
        }
    }
}
