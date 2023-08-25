package ui.notes.create

import app.cash.turbine.test
import app.cash.turbine.testIn
import app.cash.turbine.turbineScope
import domain.usecases.CreateNoteUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateNoteViewModelTest {

    private val createNoteUseCase: CreateNoteUseCase = mockk()
    private lateinit var createNoteViewModel: CreateNoteViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        createNoteViewModel = CreateNoteViewModel(createNoteUseCase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `ViewState has updated note text`() = runTest {
        val testNoteText = "Test note text"

        coEvery { createNoteUseCase.call(testNoteText) } returns Unit

        createNoteViewModel.onNoteTextUpdated(testNoteText)

        assertEquals(testNoteText, createNoteViewModel.state.value.noteText)
    }

    @Test
    fun `ViewState updated onNoteCreated success`() = runTest {
        val testNoteText = "Test note text"

        coEvery { createNoteUseCase.call(testNoteText) } returns Unit

        createNoteViewModel.state.test {
            awaitItem()
            createNoteViewModel.onNoteTextUpdated(testNoteText)
            awaitItem()
            createNoteViewModel.createNote()
            assertEquals(true, awaitItem().onNoteCreated)
        }
    }

    @Test
    fun `ViewState not updated when current text is empty`() = runTest{
        createNoteViewModel.state.test {
            createNoteViewModel.createNote()
            assertEquals(false, awaitItem().onNoteCreated)
        }
    }

    @Test
    fun `ViewState updated onNoteCreatedConsumed`() = runTest {
        val testNoteText = "Test note text"

        coEvery { createNoteUseCase.call(testNoteText) } returns Unit

        createNoteViewModel.state.test {
            awaitItem()
            createNoteViewModel.onNoteTextUpdated(testNoteText)
            awaitItem()
            createNoteViewModel.createNote()
            awaitItem()
            createNoteViewModel.onNoteCreatedConsumed()
            assertEquals(false, awaitItem().onNoteCreated)
        }
    }

    @Test
    fun `ViewState updated onCleared`() = runTest {
        val testNoteText = "Test note text"

        coEvery { createNoteUseCase.call(testNoteText) } returns Unit

        createNoteViewModel.state.test {
            awaitItem()
            createNoteViewModel.onNoteTextUpdated(testNoteText)
            awaitItem()
            createNoteViewModel.createNote()
            awaitItem()
            createNoteViewModel.onCleared()
            assertEquals(CreateNoteState(), awaitItem())
        }
    }
}
