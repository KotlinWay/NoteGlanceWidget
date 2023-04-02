package com.example.note_glance_widget.note

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateIf
import com.example.note_glance_widget.db.NoteDao
import com.example.note_glance_widget.db.NoteDb
import com.example.note_glance_widget.note.model.Note
import com.example.note_glance_widget.note.model.toEntity
import com.example.note_glance_widget.note.model.toEntityDb
import com.example.note_glance_widget.widget.NoteWidget
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteIdPK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteLastUpdatePK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteTextPK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteTitlePK
import com.example.note_glance_widget.widget.mapNoteToWidget
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotesRepository @Inject constructor(
    private val notesDao: NoteDao,
    @ApplicationContext private val context: Context
) {

    suspend fun addNewNote() = notesDao.insert(NoteDb())

    suspend fun getNote(noteId: Long) = notesDao.get(noteId)

    suspend fun updateNote(note: Note) {
        notesDao.update(note.toEntityDb())
        GlanceAppWidgetManager(context).mapNoteToWidget(context, note)
    }

    fun getNotes() = notesDao.getListFlow().map { notesDb ->
        notesDb.map { note -> note.toEntity() }
    }
}