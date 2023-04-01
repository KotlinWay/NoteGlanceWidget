package com.example.note_glance_widget.note

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.note_glance_widget.R
import com.example.note_glance_widget.note.NoteContract.*
import com.example.note_glance_widget.note.model.NoteId
import com.example.note_glance_widget.notes.FAB
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NoteScreen(
    noteId: NoteId,
    saveNoteListener: () -> Unit
) {

    val viewModel: NoteViewModel = hiltViewModel()
    var titleFieldValue by remember { mutableStateOf(TextFieldValue(text = "")) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = "")) }

    LaunchedEffect(Unit) {
        viewModel.setEvent(Event.Activate(noteId))
        viewModel.effect.onEach { effect ->
            when (effect) {
                is Effect.Activate -> {
                    titleFieldValue = TextFieldValue(effect.note.title)
                    textFieldValue = TextFieldValue(effect.note.text)
                }
                Effect.SaveNote -> saveNoteListener()
            }
        }.launchIn(this)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Card(
            backgroundColor = Color.Blue.copy(0.2f),
            modifier = Modifier.padding(16.dp)
        ) {
            Column {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
                    value = titleFieldValue,
                    onValueChange = { titleFieldValue = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                    ),
                    placeholder = { Text("Title") }
                )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                    value = textFieldValue,
                    onValueChange = { textFieldValue = it },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.White,
                    ),
                    placeholder = { Text("Content") }
                )
            }
        }

        FAB(R.drawable.ic_save) {
            viewModel.setEvent(
                Event.SaveNote(
                    title = titleFieldValue.text,
                    text = textFieldValue.text
                )
            )
        }
    }
}