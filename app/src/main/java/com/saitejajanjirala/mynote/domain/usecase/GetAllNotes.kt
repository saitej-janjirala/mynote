package com.saitejajanjirala.mynote.domain.usecase

import com.saitejajanjirala.mynote.domain.models.Note
import com.saitejajanjirala.mynote.domain.repository.NoteRepository
import com.saitejajanjirala.mynote.domain.util.NoteOrder
import com.saitejajanjirala.mynote.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetAllNotes constructor(private val repository: NoteRepository) {
    operator fun invoke(noteOrder: NoteOrder) : Flow<List<Note>>{
        return repository.getNotes().map {notes->
            when(noteOrder){
                is NoteOrder.Date -> {
                    when(noteOrder.orderType){
                        OrderType.Ascending -> {
                            notes.sortedBy { it.timeStamp }
                        }
                        OrderType.Descending ->{
                            notes.sortedByDescending { it.timeStamp }
                        }
                    }
                }
                is NoteOrder.Title -> {
                    when(noteOrder.orderType){
                        OrderType.Ascending -> {
                            notes.sortedBy { it.title }
                        }
                        OrderType.Descending -> {
                            notes.sortedByDescending { it.title }
                        }
                    }
                }
            }
        }
    }
}