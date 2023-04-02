package com.example.note_glance_widget.note

import com.example.note_glance_widget.db.NoteDao
import com.example.note_glance_widget.db.NoteDb
import com.example.note_glance_widget.note.model.Note
import com.example.note_glance_widget.note.model.toEntity
import com.example.note_glance_widget.note.model.toEntityDb
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val notesDao: NoteDao
) {

    suspend fun addNewNote() = notesDao.insert(NoteDb())

    suspend fun getNote(noteId: Long) = notesDao.get(noteId)

    suspend fun updateNote(note: Note) = notesDao.update(note.toEntityDb())

    fun getNotes() = notesDao.getListFlow().map { notesDb ->
        notesDb.map { note -> note.toEntity() }
    }
}