package com.saitejajanjirala.mynote.presentation.add_edit

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent{
    data class OnDescriptionFocusChanged(val focusState: FocusState) : AddEditNoteEvent()
    data class OnTitleFocusChanged(val focusState: FocusState) : AddEditNoteEvent()
    data class OnTitleEntered(val text: String) : AddEditNoteEvent()
    data class OnDescriptionEntered(val text : String): AddEditNoteEvent()
    object SaveNote : AddEditNoteEvent()
}
