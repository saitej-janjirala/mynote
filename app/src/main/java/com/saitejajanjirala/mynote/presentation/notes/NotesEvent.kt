package com.saitejajanjirala.mynote.presentation.notes

import com.saitejajanjirala.mynote.domain.models.Note
import com.saitejajanjirala.mynote.domain.util.NoteOrder

sealed class NotesEvent{
    data class OrderEvent(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note : Note) : NotesEvent()
    object RestoreNoteEvent : NotesEvent()
    object ToggleOrderSelection : NotesEvent()
}
