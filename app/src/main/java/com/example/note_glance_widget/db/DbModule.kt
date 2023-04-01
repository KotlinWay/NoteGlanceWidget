package com.example.note_glance_widget.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DbModule {

    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext context: Context
    ): AppDb = Room.databaseBuilder(context, AppDb::class.java, "notes.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideNoteDao(db: AppDb): NoteDao = db.getNotesDao()
}