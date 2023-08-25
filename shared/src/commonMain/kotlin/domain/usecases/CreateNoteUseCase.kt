package domain.usecases

import data.db.NotesRepository

class CreateNoteUseCase(
    private val notesRepository: NotesRepository
) {

    suspend fun call(content: String) {
        notesRepository.insertNote(content = content)
    }
}
