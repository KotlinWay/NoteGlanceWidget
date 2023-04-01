package com.example.note_glance_widget.navigation

import com.example.note_glance_widget.note.NoteScreen
import com.example.note_glance_widget.note.model.NoteId

sealed class Screens(val route: String){
    object NotesScreen: Screens("notes")
    object NoteScreen: Screens("note/{noteId}"){
        const val noteId = "noteId"
        fun withParam(noteId: NoteId) = route.replace(oldValue = "{${NoteScreen.noteId}}", newValue = "${noteId.id}")
    }
}

