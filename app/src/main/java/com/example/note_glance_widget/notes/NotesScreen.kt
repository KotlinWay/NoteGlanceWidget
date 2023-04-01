package com.example.note_glance_widget.notes

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.note_glance_widget.note.model.NoteId

@Composable
fun NotesScreen(
    navigateToNoteListener: (NoteId) -> Unit
){
    val viewModel = hiltViewModel<NotesViewModel>()

    Text("Notes")
}