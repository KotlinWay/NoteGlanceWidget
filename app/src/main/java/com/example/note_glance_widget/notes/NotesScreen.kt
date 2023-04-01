package com.example.note_glance_widget.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.note_glance_widget.note.model.NoteId

@Composable
fun NotesScreen(
    navigateToNoteListener: (NoteId) -> Unit
){
    Text("Notes")
}