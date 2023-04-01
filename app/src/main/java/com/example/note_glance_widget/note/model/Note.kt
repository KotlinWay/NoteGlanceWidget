package com.example.note_glance_widget.note.model

import com.example.note_glance_widget.db.NoteDb
import java.time.LocalDateTime

data class Note(
    val id: NoteId,
    val title: String,
    val text: String,
    val updatedAt: LocalDateTime
)

fun NoteDb.toEntity() = Note(
    id = NoteId(id),
    title = title.orEmpty(),
    text = text.orEmpty(),
    updatedAt = LocalDateTime.parse(updatedAt)
)

fun Note.toEntityDb() = NoteDb(
    id = id.id,
    title = title,
    text = text,
    updatedAt = updatedAt.toString()
)