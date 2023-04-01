package com.example.note_glance_widget.notes

import androidx.lifecycle.ViewModel
import com.example.note_glance_widget.common.application.BaseViewModel
import com.example.note_glance_widget.note.NotesRepository
import com.example.note_glance_widget.notes.NotesContract.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesRepository: NotesRepository
): BaseViewModel<Event, State, Effect>(){

    override fun setInitialState() = State.NONE

    override fun handleEvent(event: Event) = when(event){
        Event.ClickOnAddNote -> TODO()
        is Event.ClickOnNote -> TODO()
    }

}