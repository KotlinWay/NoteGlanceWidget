package com.example.note_glance_widget.note

import com.example.note_glance_widget.common.application.ViewEvent
import com.example.note_glance_widget.common.application.ViewSideEffect
import com.example.note_glance_widget.common.application.ViewState
import com.example.note_glance_widget.note.model.Note

class NoteContract {
    sealed class Event : ViewEvent {
        data class Activate(val noteId: Long) : Event()
        data class SaveNote(
            val title: String,
            val text: String
        ) : Event()

        object PinWidget : Event()
    }

    data class State(val note: Note?) : ViewState {
        companion object {
            val NONE = State(null)
        }
    }

    sealed class Effect : ViewSideEffect {
        object SaveNote : Effect()
        data class Activate(val note: Note) : Effect()
    }
}