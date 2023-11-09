package com.saitejajanjirala.mynote.di

import android.app.Application
import androidx.room.Room
import com.saitejajanjirala.mynote.data.db.NoteDao
import com.saitejajanjirala.mynote.data.db.NoteDatabase
import com.saitejajanjirala.mynote.data.repository.NoteRepositoryImpl
import com.saitejajanjirala.mynote.domain.repository.NoteRepository
import com.saitejajanjirala.mynote.domain.usecase.AddNote
import com.saitejajanjirala.mynote.domain.usecase.DeleteNote
import com.saitejajanjirala.mynote.domain.usecase.GetAllNotes
import com.saitejajanjirala.mynote.domain.usecase.GetNoteById
import com.saitejajanjirala.mynote.domain.usecase.NoteUseCases
import com.saitejajanjirala.mynote.domain.usecase.UpdateNote
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesRepository(noteDao: NoteDao) : NoteRepository{
        return NoteRepositoryImpl(noteDao)
    }

    @Provides
    @Singleton
    fun providesDatabaseDao(application : Application) : NoteDao{
       return Room.databaseBuilder(application,NoteDatabase::class.java,NoteDatabase.DB_NAME).build().noteDao;
    }

    @Provides
    @Singleton
    fun providesNoteUseCases(noteRepository: NoteRepository):NoteUseCases{
        return NoteUseCases(
            addNote = AddNote(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            updateNote = UpdateNote(noteRepository),
            getAllNotes = GetAllNotes(noteRepository),
            getNoteById = GetNoteById(noteRepository)
        )
    }
}