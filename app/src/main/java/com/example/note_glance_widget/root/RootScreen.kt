package com.example.note_glance_widget.root

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.note_glance_widget.navigation.Screens
import com.example.note_glance_widget.note.NoteScreen
import com.example.note_glance_widget.note.model.NoteId
import com.example.note_glance_widget.notes.NotesScreen

@Composable
fun RootScreen() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.NotesScreen.route
    ) {
        composable(route = Screens.NotesScreen.route) {
            NotesScreen { noteId ->
                navController.navigate(Screens.NoteScreen.withParam(noteId))
            }
        }

        composable(
            route = Screens.NoteScreen.route,
            arguments = listOf(navArgument(Screens.NoteScreen.noteId) {
                defaultValue = NoteId.NONE.id
                type = NavType.LongType
            })
        ) { backStackEntry ->
            NoteScreen(
                noteId = backStackEntry.arguments?.getLong(Screens.NoteScreen.noteId)
                    ?.let { NoteId(it) } ?: NoteId.NONE) {
                navController.popBackStack()
            }
        }
    }
}