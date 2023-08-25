package ui.notes.overview

import com.myapplication.Note
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.coroutineScope
import domain.usecases.WatchNotesUseCase
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class NotesOverviewViewModel(
    watchNotesUseCase: WatchNotesUseCase
) : KMMViewModel() {

    val notes: StateFlow<ImmutableList<Note>> = watchNotesUseCase.call()
        .map(List<Note>::toImmutableList)
        .stateIn(
            viewModelScope.coroutineScope,
            SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000L),
            persistentListOf()
        )
}
