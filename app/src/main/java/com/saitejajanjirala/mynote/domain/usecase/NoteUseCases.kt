package com.saitejajanjirala.mynote.domain.usecase

data class NoteUseCases(
    val addNote: AddNote,
    val deleteNote: DeleteNote,
    val getAllNotes: GetAllNotes,
    val updateNote : UpdateNote,
    val getNoteById: GetNoteById
)
