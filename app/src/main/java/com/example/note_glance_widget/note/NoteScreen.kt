package com.example.note_glance_widget.note

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.example.note_glance_widget.note.model.NoteId

@Composable
fun NoteScreen(noteId: NoteId) {
    Text("$noteId")
}