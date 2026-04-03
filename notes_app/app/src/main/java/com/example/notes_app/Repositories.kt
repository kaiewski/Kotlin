package com.example.notes_app

import android.content.Context
import androidx.room.Room
import com.example.notes_app.room.db_helper.AppDatabase
import com.example.notes_app.room.db_helper.NotesDao

object Repositories {
    private lateinit var applicationContext: Context

    private val appDatabase: AppDatabase by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "Notes.db").build()
    }

    fun noteDao(): NotesDao {
        return appDatabase.getNotesDao()
    }

    fun initialize(context: Context){
        applicationContext = context.applicationContext
    }
}