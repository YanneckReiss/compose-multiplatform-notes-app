package domain.usecases

import com.myapplication.Note
import data.db.NotesRepository
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Clock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class WatchNotesUseCaseTest {

    private lateinit var watchNoteUseCase: WatchNotesUseCase

    private val notesRepository: NotesRepository = mockk()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        watchNoteUseCase = WatchNotesUseCase(notesRepository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Notes from database are observed correctly`() = runTest {

        val testNotes: List<Note> = listOf(
            Note(
                id = 1,
                content = "test content",
                date_created = Clock.System.now()
            ),
            Note(
                id = 2,
                content = "test content",
                date_created = Clock.System.now()
            ),
        )

        every { notesRepository.watchNotes() } returns flowOf(testNotes)

        val result: Flow<List<Note>> = watchNoteUseCase.call()

        assertEquals(testNotes, result.first())
    }
}