package com.saitejajanjirala.mynote.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saitejajanjirala.mynote.domain.models.Note
import com.saitejajanjirala.mynote.domain.usecase.NoteUseCases
import com.saitejajanjirala.mynote.domain.util.NoteOrder
import com.saitejajanjirala.mynote.domain.util.OrderType
import com.saitejajanjirala.mynote.presentation.notes.NotesEvent
import com.saitejajanjirala.mynote.presentation.notes.NotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val noteUseCases : NoteUseCases): ViewModel() {
    private val _notesState = mutableStateOf(NotesState())
    val notesState : State<NotesState>
        get() = _notesState
    private var recentlyDeletedNote : Note? = null
    private var notesJob : Job? = null
    init {
        fetchNotesByOrderType(NoteOrder.Date(OrderType.Descending))
    }

    private fun fetchNotesByOrderType(noteOrder : NoteOrder) {
        notesJob?.cancel()
        notesJob = noteUseCases.getAllNotes.invoke(noteOrder).onEach {
            _notesState.value = notesState.value.copy(
                notes = it,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: NotesEvent){
        when(event){
            is NotesEvent.DeleteNote -> {
                viewModelScope.launch {
                    noteUseCases.deleteNote.invoke(event.note)
                    recentlyDeletedNote = event.note
                }
            }
            is NotesEvent.OrderEvent -> {
                if(notesState.value::class == event.noteOrder::class
                    && notesState.value.noteOrder == event.noteOrder.orderType
                ){
                    return
                }
                fetchNotesByOrderType(event.noteOrder)
            }
            NotesEvent.RestoreNoteEvent -> {
                viewModelScope.launch {
                    noteUseCases.addNote.invoke(recentlyDeletedNote?:return@launch)
                    recentlyDeletedNote = null
                }
            }
            NotesEvent.ToggleOrderSelection ->{
                _notesState.value = notesState.value.copy(
                    isOrderSelectionVisible = !notesState.value.isOrderSelectionVisible
                )
            }
        }
    }


}