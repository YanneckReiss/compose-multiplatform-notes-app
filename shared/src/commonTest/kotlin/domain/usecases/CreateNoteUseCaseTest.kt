package domain.usecases

import data.db.NotesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class CreateNoteUseCaseTest {

    private lateinit var createNoteUseCase: CreateNoteUseCase

    private val notesRepository: NotesRepository = mockk()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        createNoteUseCase = CreateNoteUseCase(notesRepository)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Notes get created successfully`() = runTest {

        val testNote = "test content"

        coEvery { createNoteUseCase.call(testNote) } returns Unit

        createNoteUseCase.call(testNote)

        coVerify { notesRepository.insertNote(testNote) }
    }
}
