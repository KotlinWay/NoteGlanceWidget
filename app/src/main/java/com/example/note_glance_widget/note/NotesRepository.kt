package com.example.note_glance_widget.note

import com.example.note_glance_widget.db.NoteDao
import com.example.note_glance_widget.note.model.toEntity
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val notesDao: NoteDao
) {
    fun getNotes() = notesDao.getListFlow().map { notesDb ->
        notesDb.map { note -> note.toEntity() }
    }
}