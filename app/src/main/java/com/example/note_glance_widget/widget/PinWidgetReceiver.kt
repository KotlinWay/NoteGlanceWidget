package com.example.note_glance_widget.widget

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import com.example.note_glance_widget.note.NotesRepository
import com.example.note_glance_widget.note.model.Note
import com.example.note_glance_widget.note.model.toEntity
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteIdPK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteLastUpdatePK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteTextPK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteTitlePK
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext

const val NOTE_ID = "NOTE_ID"

@AndroidEntryPoint
class PinWidgetReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: NotesRepository

    override fun onReceive(context: Context, intent: Intent) {
        val noteId = intent.getLongExtra(NOTE_ID, Long.MIN_VALUE)
        CoroutineScope(EmptyCoroutineContext).launch {
            delay(3000)
            val note = repository.getNote(noteId)?.let { it.toEntity() } ?: return@launch
            val glanceManager = GlanceAppWidgetManager(context)
            val lastAddedGlanceId = glanceManager.getGlanceIds(NoteWidget::class.java).last()
            mapNoteToWidget(context, lastAddedGlanceId, note)
        }
    }

    private suspend fun mapNoteToWidget(context: Context, lastAddedGlanceId: GlanceId, note: Note) {
        updateAppWidgetState(context, lastAddedGlanceId) { prefs ->
            prefs[noteIdPK] = note.id
            prefs[noteTitlePK] = note.title
            prefs[noteTextPK] = note.text
            prefs[noteLastUpdatePK] = note.formatUpdatedAt
        }
        NoteWidget().update(context, lastAddedGlanceId)
    }
}