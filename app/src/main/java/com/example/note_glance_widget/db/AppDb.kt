package com.example.note_glance_widget.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [NoteDb::class],
    version = 1,
    exportSchema = false
)
abstract class AppDb: RoomDatabase() {
    abstract fun getNotesDao(): NoteDao
}