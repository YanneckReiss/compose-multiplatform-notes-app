package ui.navigation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NavigatorTest {

    private lateinit var navigator: Navigator

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        navigator = Navigator()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Route gets pushed on backstack on navigation`() = runTest {

        val testRoute: Navigator.Routes = Navigator.Routes.CREATE_NOTES

        navigator.navigateTo(testRoute)

        assertEquals(testRoute, navigator.backStack.first().last())
    }

    @Test
    fun `Route gets popped from backstack on back navigation`() = runTest {

        val testRoute: Navigator.Routes = Navigator.Routes.CREATE_NOTES

        navigator.navigateTo(testRoute)
        navigator.navigateBack()

        assertEquals(Navigator.Routes.OVERVIEW_NOTES, navigator.backStack.first().last())
    }

    @Test
    fun `Route does not get popped from backstack on back navigation if only one route is on the stack`() = runTest {

        navigator.navigateBack()

        assertEquals(Navigator.Routes.OVERVIEW_NOTES, navigator.backStack.first().last())
    }

    @Test
    fun `Route does not get pushed on backstack on navigation if route is already on the stack`() = runTest {

        val testRoute: Navigator.Routes = Navigator.Routes.CREATE_NOTES

        navigator.navigateTo(testRoute)
        navigator.navigateTo(testRoute)

        assertEquals(2, navigator.backStack.first().size)
    }
}
