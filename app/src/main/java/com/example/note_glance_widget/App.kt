package com.example.note_glance_widget

import android.app.Application
import com.example.note_glance_widget.note.NotesRepository
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App: Application() {

    @Inject
    lateinit var notesRepository: NotesRepository
}