package domain.usecases

import com.myapplication.Note
import data.db.NotesRepository
import kotlinx.coroutines.flow.Flow

class WatchNotesUseCase(
    private val notesRepository: NotesRepository
) {

    fun call(): Flow<List<Note>> = notesRepository.watchNotes()
}
