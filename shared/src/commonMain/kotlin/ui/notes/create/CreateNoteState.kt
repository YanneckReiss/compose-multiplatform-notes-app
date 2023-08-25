package ui.notes.create

data class CreateNoteState(
    val noteText: String = "",
    val onNoteCreated: Boolean = false
)
