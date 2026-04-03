package com.example.notes_app.room.db_helper

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [NoteDbEntity::class]
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getNotesDao(): NotesDao
}