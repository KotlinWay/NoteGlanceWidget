package com.example.note_glance_widget.note.model

@JvmInline
value class NoteId(val id: Long){
    companion object{
        val NONE = NoteId(Long.MIN_VALUE)
    }
}