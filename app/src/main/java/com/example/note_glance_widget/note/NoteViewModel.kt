package com.example.note_glance_widget.note

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.lifecycle.viewModelScope
import com.example.note_glance_widget.common.application.BaseViewModel
import com.example.note_glance_widget.note.NoteContract.*
import com.example.note_glance_widget.note.model.toEntity
import com.example.note_glance_widget.widget.NOTE_ID
import com.example.note_glance_widget.widget.NoteWidgetReceiver
import com.example.note_glance_widget.widget.PinWidgetReceiver
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
        val noteId = currentState().note?.id ?: return
        val intent = Intent(context, PinWidgetReceiver::class.java)
        intent.putExtra(NOTE_ID, currentState().note?.id)
        val pendingIntent = PendingIntent.getBroadcast(context, noteId.toInt(), intent, PendingIntent.FLAG_IMMUTABLE)
        GlanceAppWidgetManager(context).requestPinGlanceAppWidget(
            NoteWidgetReceiver::class.java,
            successCallback = pendingIntent
        )
    }

    private fun activate(noteId: Long) {
        if (noteId == Long.MIN_VALUE) obtainNewNote() else bindNoteToState(noteId)
    }

    private fun obtainNewNote() = viewModelScope.launch{
        val newNoteId = notesRepository.addNewNote()
        val note = notesRepository.getNote(newNoteId)?.toEntity() ?: return@launch
        updateState { copy(note = note) }
        setEffect { Effect.Activate(note) }
    }

    private fun bindNoteToState(noteId: Long) = viewModelScope.launch {
        val note = notesRepository.getNote(noteId)?.let { it.toEntity() } ?: return@launch
        updateState { copy(note = note) }
        setEffect { Effect.Activate(note) }
    }
}
