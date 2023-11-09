package com.saitejajanjirala.mynote.presentation.add_edit

data class NoteTextFieldState(
    var text : String = "",
    var hint : String = "",
    var isHintVisible : Boolean = true
)
