package com.example.note_glance_widget.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.glance.GlanceModifier
import androidx.glance.ImageProvider
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.unit.ColorProvider
import androidx.glance.background
import androidx.glance.layout.Column
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import com.example.note_glance_widget.R
import com.example.note_glance_widget.note.model.NoteId
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteIdPK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteLastUpdatePK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteTextPK
import com.example.note_glance_widget.widget.WidgetKeys.Prefs.noteTitlePK

@Composable
fun NoteWidgetContent(prefs: Preferences) {

    val noteId = prefs[noteIdPK] ?: NoteId.NONE.id
    val noteTitle = prefs[noteTitlePK].orEmpty()
    val noteText = prefs[noteTextPK].orEmpty()
    val updatedAt = prefs[noteLastUpdatePK].orEmpty()

    LazyColumn(
        modifier = GlanceModifier
            .background(imageProvider = ImageProvider(R.drawable.widget_background))
            .padding(16.dp)
    ) {
        if (noteTitle.isNotEmpty()) item {
            WidgetText(noteTitle)
        }
        if (noteText.isNotEmpty()) item {
            WidgetText(noteText)
        }
        if (updatedAt.isNotEmpty()) item {
            WidgetText(updatedAt, 16.sp)
        }
    }
}

@Composable
fun WidgetText(text: String, fontSize: TextUnit = 20.sp) {
    Text(
        text = text,
        style = TextStyle(
            fontWeight = FontWeight.Normal,
            fontSize = fontSize,
            textAlign = TextAlign.Start,
            color = ColorProvider(
                day = Color.White,
                night = Color.White
            )
        )
    )
}