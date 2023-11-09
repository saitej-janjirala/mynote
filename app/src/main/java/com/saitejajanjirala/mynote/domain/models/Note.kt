package com.saitejajanjirala.mynote.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    val title : String,
    val description : String,
    val timeStamp : Long,
    @PrimaryKey
    val id : Int? = 0
){

}
class InvalidNoteException(msg : String) : Exception(msg){

}