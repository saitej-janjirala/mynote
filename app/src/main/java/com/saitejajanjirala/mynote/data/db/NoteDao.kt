package com.saitejajanjirala.mynote.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.saitejajanjirala.mynote.domain.models.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert
    fun insertNote(note : Note)

    @Query("Select * from notes where id = :id")
    fun getNoteById(id : Int) : Note

    @Query("Select * from notes")
    fun getNotes() : Flow<List<Note>>

    @Delete
    fun deleteNote(note: Note)

    @Update
    fun updateNote(note : Note)
}