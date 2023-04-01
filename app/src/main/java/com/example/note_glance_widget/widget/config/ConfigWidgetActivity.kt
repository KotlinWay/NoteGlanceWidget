package com.example.note_glance_widget.widget.config

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.lifecycle.lifecycleScope
import com.example.note_glance_widget.note.NotesRepository
import com.example.note_glance_widget.note.model.NoteId
import com.example.note_glance_widget.note.model.toEntity
import com.example.note_glance_widget.notes.NotesScreen
import com.example.note_glance_widget.notes.NotesViewMode
import com.example.note_glance_widget.widget.NoteWidget
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteIdPK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteLastUpdatePK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteTextPK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteTitlePK
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ConfigWidgetActivity : AppCompatActivity() {

    @Inject
    lateinit var repository: NotesRepository

    private var widgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    private val result = Intent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivity()
        setContent {
            MaterialTheme {
                NotesScreen(NotesViewMode.VIEW) { id ->
                    handleSelectNote(id)
                }
            }
        }
    }

    private fun handleSelectNote(id: NoteId) {
        setResult(RESULT_OK, result)
        finish()
        saveWidgetState(id)
    }

    private fun saveWidgetState(id: NoteId) = lifecycleScope.launch(Dispatchers.IO) {
        val glanceId = GlanceAppWidgetManager(applicationContext).getGlanceIdBy(widgetId)
        val note = repository.getNote(id)?.let { it.toEntity() } ?: return@launch
        updateAppWidgetState(applicationContext, glanceId) { prefs ->
            prefs[noteIdPK] = id.id
            prefs[noteTitlePK] = note.title
            prefs[noteTextPK] = note.text
            prefs[noteLastUpdatePK] = note.formatUpdatedAt
        }
        NoteWidget().update(applicationContext, glanceId)
    }


    private fun setupActivity() {
        setResult(RESULT_CANCELED, result)
        getWidgetId()
        initResult()
    }

    private fun getWidgetId() {
        widgetId = intent.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: return
        if (widgetId == AppWidgetManager.INVALID_APPWIDGET_ID) finish()
    }

    private fun initResult() = result.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
}