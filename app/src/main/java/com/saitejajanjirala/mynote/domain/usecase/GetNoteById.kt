package com.saitejajanjirala.mynote.domain.usecase

import com.saitejajanjirala.mynote.domain.models.Note
import com.saitejajanjirala.mynote.domain.repository.NoteRepository

class GetNoteById constructor(private val repository: NoteRepository) {
    suspend fun invoke(id : Int) : Note
    {
        return repository.getNoteById(id)
    }
}