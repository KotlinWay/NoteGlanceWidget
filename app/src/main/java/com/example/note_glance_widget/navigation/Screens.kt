package com.example.note_glance_widget.navigation

sealed class Screens(val route: String){
    object NotesScreen: Screens("notes")
    object NoteScreen: Screens("note/{noteId}"){
        const val noteId = "noteId"
        fun withParam(noteId: Long) = route.replace(oldValue = "{${NoteScreen.noteId}}", newValue = "$noteId")
    }
}

