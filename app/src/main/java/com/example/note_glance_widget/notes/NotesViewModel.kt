package com.example.note_glance_widget.notes

import androidx.lifecycle.viewModelScope
import com.example.note_glance_widget.common.application.BaseViewModel
import com.example.note_glance_widget.note.NotesRepository
import com.example.note_glance_widget.notes.NotesContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
) : BaseViewModel<Event, State, Effect>() {


    init {
        bindNotesToState()
    }

    override fun setInitialState() = State.NONE

    override fun handleEvent(event: Event) = when (event) {
        Event.ClickOnAddNote -> setEffect { Effect.CreateNewNote }
        is Event.ClickOnNote -> setEffect { Effect.OpenNote(event.noteId) }
    }

    private fun bindNotesToState() = notesRepository.getNotes().onEach { notes ->
        updateState { copy(notes = notes) }
    }.launchIn(viewModelScope)
}