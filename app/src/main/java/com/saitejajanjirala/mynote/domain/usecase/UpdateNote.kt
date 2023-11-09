package com.saitejajanjirala.mynote.domain.usecase

import com.saitejajanjirala.mynote.domain.models.InvalidNoteException
import com.saitejajanjirala.mynote.domain.models.Note
import com.saitejajanjirala.mynote.domain.repository.NoteRepository

class UpdateNote constructor(private val repository: NoteRepository) {
    suspend fun invoke(note : Note){
        if(note.title.isBlank()){
            throw InvalidNoteException("Note title can't be empty")
        }
        if(note.description.isBlank()){
            throw InvalidNoteException("Note Description can't be empty")
        }
        repository.updateNote(note)
    }
}