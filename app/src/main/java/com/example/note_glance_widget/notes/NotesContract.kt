package com.example.note_glance_widget.notes

import com.example.note_glance_widget.common.application.ViewEvent
import com.example.note_glance_widget.common.application.ViewSideEffect
import com.example.note_glance_widget.common.application.ViewState
import com.example.note_glance_widget.note.model.Note

class NotesContract {
    sealed class Event : ViewEvent {
        data class ClickOnNote(val noteId: Long) : Event()
        object ClickOnAddNote : Event()
    }

    data class State(
        val notes: List<Note>
    ) : ViewState {
        companion object {
            val NONE = State(emptyList())
        }
    }

    sealed class Effect : ViewSideEffect {
        data class OpenNote(val noteId: Long): Effect()
        object CreateNewNote: Effect()
    }
}