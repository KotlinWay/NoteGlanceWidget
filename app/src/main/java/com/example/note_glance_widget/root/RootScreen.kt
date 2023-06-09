package com.example.note_glance_widget.root

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.note_glance_widget.navigation.Screens
import com.example.note_glance_widget.note.NoteScreen
import com.example.note_glance_widget.notes.NotesScreen
import com.example.note_glance_widget.notes.NotesViewMode

@Composable
fun RootScreen(startWithNoteId: Long) {

    val navController = rememberNavController()

    LaunchedEffect(startWithNoteId) {
        if (startWithNoteId != Long.MIN_VALUE)
            navController.navigate(Screens.NoteScreen.withParam(startWithNoteId))
    }

    NavHost(
        navController = navController,
        startDestination = Screens.NotesScreen.route
    ) {
        composable(route = Screens.NotesScreen.route) {
            NotesScreen(viewMode = NotesViewMode.CREATION) { noteId ->
                navController.navigate(Screens.NoteScreen.withParam(noteId))
            }
        }

        composable(
            route = Screens.NoteScreen.route,
            arguments = listOf(navArgument(Screens.NoteScreen.noteId) {
                defaultValue = Long.MIN_VALUE
                type = NavType.LongType
            })
        ) { backStackEntry ->
            NoteScreen(
                noteId = backStackEntry.arguments?.getLong(Screens.NoteScreen.noteId)
                    ?: Long.MIN_VALUE
            ) {
                navController.popBackStack()
            }
        }
    }
}