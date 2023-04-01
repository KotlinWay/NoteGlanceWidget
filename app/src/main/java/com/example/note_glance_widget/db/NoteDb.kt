package com.example.note_glance_widget.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class NoteDb(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String? = null,
    val text: String? = null,
    val updatedAt: String = LocalDateTime.now().toString()
)