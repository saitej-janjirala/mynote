package com.saitejajanjirala.mynote.presentation.notes

import com.saitejajanjirala.mynote.domain.models.Note
import com.saitejajanjirala.mynote.domain.util.NoteOrder
import com.saitejajanjirala.mynote.domain.util.OrderType

data class NotesState(
    val notes : List<Note> = emptyList(),
    val noteOrder : NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSelectionVisible : Boolean = false
)
