package ui.notes.overview

import app.cash.turbine.test
import com.myapplication.Note
import domain.usecases.WatchNotesUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Clock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class NotesOverviewViewModelTest {

    private val watchNotesUseCase: WatchNotesUseCase = mockk()
    private lateinit var notesOverviewViewModel: NotesOverviewViewModel

    private val testNotes = listOf(
        Note(
            id = 1,
            content = "Test note 1",
            date_created = Clock.System.now(),
        ),
        Note(
            id = 2,
            content = "Test note 2",
            date_created = Clock.System.now(),
        ),
    )

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        every { watchNotesUseCase.call() } returns flowOf(testNotes)
        notesOverviewViewModel = NotesOverviewViewModel(watchNotesUseCase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Notes StateFlow contains correct ImmutableList values from use case`() = runTest {
        notesOverviewViewModel.notes.test {
            assertEquals(testNotes.toImmutableList(), awaitItem())
        }
    }
}
