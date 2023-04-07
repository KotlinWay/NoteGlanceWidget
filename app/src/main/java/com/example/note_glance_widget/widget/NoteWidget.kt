package com.example.note_glance_widget.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.datastore.preferences.core.Preferences
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateIf
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import androidx.glance.state.PreferencesGlanceStateDefinition
import com.example.note_glance_widget.note.model.Note
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteIdPK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteLastUpdatePK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteTextPK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteTitlePK

class NoteWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<*> = PreferencesGlanceStateDefinition

    @Composable
    override fun Content() {
        val prefs = currentState<Preferences>()

        NoteWidgetContent(prefs)
    }
}

class NoteWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = NoteWidget()
}

suspend fun GlanceAppWidgetManager.mapNoteToWidget(context: Context, note: Note) =
    getGlanceIds(NoteWidget::class.java)
        .forEach { glanceId ->
            updateAppWidgetState(context, glanceId) { prefs ->
                prefs[noteTitlePK] = note.title
                prefs[noteTextPK] = note.text
                prefs[noteLastUpdatePK] = note.formatUpdatedAt
            }
            NoteWidget().updateIf<Preferences>(context) {
                it[noteIdPK] == note.id
            }
        }
