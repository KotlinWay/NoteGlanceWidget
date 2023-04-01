package com.example.note_glance_widget.notes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.note_glance_widget.R
import com.example.note_glance_widget.note.model.Note
import com.example.note_glance_widget.note.model.NoteId
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NotesScreen(
    navigateToNoteListener: (NoteId) -> Unit
) {
    val viewModel = hiltViewModel<NotesViewModel>()

    LaunchedEffect(Unit) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                NotesContract.Effect.CreateNewNote -> navigateToNoteListener(NoteId.NONE)
                is NotesContract.Effect.OpenNote -> navigateToNoteListener(effect.noteId)
            }
        }.launchIn(this)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Notes(viewModel)
        FAB(viewModel)
    }
}

@Composable
fun BoxScope.Notes(viewModel: NotesViewModel){
    val state by viewModel.viewState

    LazyColumn {
        state.notes.forEach { note ->
            item(key = note.id) {
                NoteItem(note)
            }
        }
    }
}

@Composable
fun NoteItem(note: Note) {
    Card(backgroundColor = Color.Blue.copy(0.5f)) {
        Column(modifier = Modifier.padding(16.dp)) {
            with(note) {
                if (title.isNotEmpty()) Text(title, modifier = Modifier.padding(bottom = 16.dp))
                if (text.isNotEmpty()) Text(text, modifier = Modifier.padding(bottom = 16.dp))
                Text(updatedAt.toString())
            }
        }
    }
}

@Composable
fun BoxScope.FAB(
    viewModel: NotesViewModel
) {
    FloatingActionButton(
        onClick = { viewModel.setEvent(NotesContract.Event.ClickOnAddNote) },
        backgroundColor = Color.Blue,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(18.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = null,
            modifier = Modifier.size(36.dp),
            colorFilter = ColorFilter.tint(Color.White)
        )
    }
}