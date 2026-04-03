package com.example.notes_app

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.notes_app.room.db_helper.NoteDbEntity
import com.example.notes_app.room.db_helper.NotesDao
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var addButton: Button
    private lateinit var notesDao: NotesDao
    private lateinit var listNotes: ListView
    private lateinit var notes: List<NoteDbEntity?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        onResume()

        addButton.setOnClickListener {
            buttonAddClickHandler()
        }

        listNotes.setOnItemClickListener { adapterView, view, i, l ->
            listNotesClickHandler(i)
        }
    }

    private fun inicializeInterface(){
        Repositories.initialize(this)
        notesDao = Repositories.noteDao()
        addButton = findViewById(R.id.buttonAdd)
        listNotes = findViewById(R.id.listNotes)
    }
    private fun buttonAddClickHandler(){
        lifecycleScope.launch {
            notesDao.addNewNote(NoteDbEntity(0, "", ""))
            val maxID = notesDao.getMaxId()
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            intent.putExtra("NOTE_ID", maxID)
            startActivity(intent)
        }
    }

    private fun listNotesClickHandler(i: Int){
        lifecycleScope.launch{
            val noteId = notes[i]?.id
            val intent = Intent(this@MainActivity, AddNoteActivity::class.java)
            intent.putExtra("NOTE_ID", noteId)
            startActivity(intent)
        }
    }

    private fun showAllNotes() {
        lifecycleScope.launch {
            notes = notesDao.getAllNotes()
            val noteNames: List<String> = notes.filterNotNull().map { noteDbEntity -> noteDbEntity.name}
            val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, noteNames)
            listNotes.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        inicializeInterface()
        showAllNotes()
    }
}