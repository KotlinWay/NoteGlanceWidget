package com.example.note_glance_widget.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String?,
    val text: String?,
    val updatedAt: String
)