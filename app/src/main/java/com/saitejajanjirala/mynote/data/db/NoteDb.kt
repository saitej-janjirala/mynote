package com.saitejajanjirala.mynote.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saitejajanjirala.mynote.domain.models.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NoteDatabase : RoomDatabase() {
    abstract val noteDao : NoteDao
    companion object{
        const val DB_NAME = "SJ-DB"
    }
}