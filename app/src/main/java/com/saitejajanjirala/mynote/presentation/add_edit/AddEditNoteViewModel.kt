package com.saitejajanjirala.mynote.presentation.add_edit

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saitejajanjirala.mynote.domain.models.InvalidNoteException
import com.saitejajanjirala.mynote.domain.models.Note
import com.saitejajanjirala.mynote.domain.usecase.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    val noteUseCases: NoteUseCases,
    val savedStateHandle: SavedStateHandle
    ) : ViewModel() {

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter your title here..."
        )
    )

    val noteTitle : State<NoteTextFieldState>
        get() = _noteTitle


    private val _noteDescription = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter your Description here..."
        )
    )

    val noteDescription : State<NoteTextFieldState>
        get() = _noteDescription

    private  val _eventFlow = MutableSharedFlow<UiEvent>()

    val eventFLow : SharedFlow<UiEvent>
        get() = _eventFlow

    private var currentNoteId : Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let {noteId->
            viewModelScope.launch(Dispatchers.IO) {
                noteUseCases.getNoteById.invoke(noteId)?.also{note->
                    currentNoteId = note.id
                    _noteTitle.value = noteTitle.value.copy(
                        text = note.title,
                        isHintVisible = false,
                    )
                    _noteDescription.value = noteDescription.value.copy(
                        text = note.description,
                        isHintVisible = false,
                    )
                }
            }
        }
    }

    fun onEvent(event : AddEditNoteEvent){
        when(event){
            is AddEditNoteEvent.OnDescriptionEntered ->{
               _noteDescription.value = noteDescription.value.copy(
                    text = event.text
                )
            }
            is AddEditNoteEvent.OnDescriptionFocusChanged -> {
                _noteDescription.value = noteDescription.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteDescription.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.OnTitleEntered -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.text
                )
            }
            is AddEditNoteEvent.OnTitleFocusChanged -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        noteUseCases.addNote.invoke(
                            Note(
                               title = noteTitle.value.text,
                                id = currentNoteId,
                                description = noteDescription.value.text,
                                timeStamp = System.currentTimeMillis()
                            )
                        )
                        _eventFlow.emit(
                            UiEvent.SaveNote
                        )
                    }catch (e : InvalidNoteException){
                        _eventFlow.emit(
                            UiEvent.ShowSnackBar(e.message.toString())
                        )
                    }
                }
            }
        }
    }

}

sealed class UiEvent{
    object SaveNote : UiEvent()
    data class ShowSnackBar(val msg : String) : UiEvent()
}