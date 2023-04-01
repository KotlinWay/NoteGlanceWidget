package com.example.note_glance_widget.note.model

import com.example.note_glance_widget.db.NoteDb
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Note(
    val id: NoteId,
    val title: String,
    val text: String,
    val updatedAt: LocalDateTime
){
    val formatUpdatedAt: String
        get() = "Обновлено: ${updatedAt.format(DateTimeFormatter.ofPattern("dd MMMM yyyy в HH:mm"))}"
}

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