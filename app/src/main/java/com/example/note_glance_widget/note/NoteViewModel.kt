package com.example.note_glance_widget.note

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.example.note_glance_widget.common.application.BaseViewModel
import com.example.note_glance_widget.note.NoteContract.*
import com.example.note_glance_widget.note.model.NoteId
import com.example.note_glance_widget.note.model.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val notesRepository: NotesRepository,
    @ApplicationContext private val context: Context
) : BaseViewModel<Event, State, Effect>() {

    override fun setInitialState() = State.NONE

    override fun handleEvent(event: Event) = when (event) {
        is Event.Activate -> activate(event.noteId)
        Event.PinWidget -> handlePinWidget()
        is Event.SaveNote -> handleSaveNote(event.title, event.text)
    }

    private fun handleSaveNote(title: String, text: String) {
        viewModelScope.launch {
            val note = currentState().note?.copy(
                title = title,
                text = text,
                updatedAt = LocalDateTime.now()
            ) ?: return@launch
            notesRepository.updateNote(note)
            setEffect { Effect.SaveNote }
        }
    }

    private fun handlePinWidget() {
        TODO("Not yet implemented")
    }

    private fun activate(noteId: NoteId) {
        if (noteId == NoteId.NONE) obtainNewNote() else bindNoteToState(noteId)
    }

    private fun obtainNewNote() = viewModelScope.launch{
        val newNoteId = notesRepository.addNewNote()
        val note = notesRepository.getNote(newNoteId)?.toEntity() ?: return@launch
        updateState { copy(note = note) }
        setEffect { Effect.Activate(note) }
    }

    private fun bindNoteToState(noteId: NoteId) = viewModelScope.launch {
        val note = notesRepository.getNote(noteId)?.let { it.toEntity() } ?: return@launch
        updateState { copy(note = note) }
        setEffect { Effect.Activate(note) }
    }
}
