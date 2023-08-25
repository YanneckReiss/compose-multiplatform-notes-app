package data.db

import app.cash.sqldelight.coroutines.asFlow
import com.myapplication.Note
import com.myapplication.NoteQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import org.koin.core.component.KoinComponent

class NotesRepository(
    private val noteQueries: NoteQueries
) : KoinComponent {

    fun watchNotes(): Flow<List<Note>> = noteQueries
        .findAll()
        .asFlow()
        .map { noteQuery ->
            withContext(Dispatchers.IO) {
                noteQuery.executeAsList()
            }
        }

    suspend fun insertNote(
        content: String
    ) {
        withContext(Dispatchers.IO) {
            noteQueries.insert(
                id = null,
                content = content,
                date_created = Clock.System.now()
            )
        }
    }
}
