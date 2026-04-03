package com.example.notes_app.room.db_helper

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Notes"
)
data class NoteDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val text: String
)
