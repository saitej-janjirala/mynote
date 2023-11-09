package com.saitejajanjirala.mynote.domain.usecase

import com.saitejajanjirala.mynote.domain.models.Note
import com.saitejajanjirala.mynote.domain.repository.NoteRepository

class DeleteNote constructor(private val repository: NoteRepository) {
    suspend operator fun invoke(note : Note){
        repository.deleteNote(note)
    }
}