package com.example.note_glance_widget.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteDb: NoteDb): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(noteDb: NoteDb)

    @Query("SELECT * FROM NoteDb where id = :id LIMIT 1")
    suspend fun get(id: Long): NoteDb?

    @Query("SELECT * FROM NoteDb where id = :id LIMIT 1")
    fun getFlow(id: Long): Flow<NoteDb?>

    @Query("SELECT * FROM NoteDb")
    fun getListFlow(): Flow<List<NoteDb>>

    @Delete
    suspend fun delete(noteDb: NoteDb)
}