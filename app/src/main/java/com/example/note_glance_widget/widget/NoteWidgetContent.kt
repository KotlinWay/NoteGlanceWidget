package com.example.note_glance_widget.widget

import androidx.compose.runtime.Composable
import androidx.datastore.preferences.core.Preferences
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.layout.Column
import androidx.glance.text.Text
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteIdPK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteLastUpdatePK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteTextPK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteTitlePK

@Composable
fun NoteWidgetContent(prefs: Preferences, noteId: Long) {

    val noteTitle = prefs[noteTitlePK].orEmpty()
    val noteText = prefs[noteTextPK].orEmpty()
    val updatedAt = prefs[noteLastUpdatePK].orEmpty()

    LazyColumn {
        if (noteTitle.isNotEmpty()) item {
            Text(noteTitle)
        }
        if (noteText.isNotEmpty()) item {
            Text(noteText)
        }
        if (updatedAt.isNotEmpty()) item {
            Text(updatedAt)
        }
    }
    Text("note widget")
}