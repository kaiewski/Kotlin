package com.example.notes_app.room.db_helper

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM Notes WHERE id = :id")
    suspend fun getNoteById(id: Int): NoteDbEntity?

    @Query("SELECT * FROM Notes")
    suspend fun getAllNotes(): List<NoteDbEntity?>

    @Update(entity = NoteDbEntity::class)
    suspend fun editNoteNameById(changeNoteName: ChangeNoteName)

    @Update(entity = NoteDbEntity::class)
    suspend fun editNoteTextById(changeNoteText: ChangeNoteText)

    @Insert
    suspend fun addNewNote(noteDbEntity: NoteDbEntity)

    @Query("SELECT MAX(id) FROM Notes")
    suspend fun getMaxId(): Int?

    @Query("DELETE FROM Notes WHERE id = :id")
    suspend fun deleteNoteById(id: Int)
}