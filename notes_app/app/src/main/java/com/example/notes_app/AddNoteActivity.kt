package com.example.notes_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.notes_app.room.db_helper.ChangeNoteName
import com.example.notes_app.room.db_helper.ChangeNoteText
import com.example.notes_app.room.db_helper.NoteDbEntity
import com.example.notes_app.room.db_helper.NotesDao
import kotlinx.coroutines.launch


class AddNoteActivity : AppCompatActivity() {
    private lateinit var textName: EditText
    private lateinit var textNote: EditText
    private lateinit var notesDao: NotesDao
    private lateinit var buttonDelete: Button
    private var currentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_note)

        inicializeInterface()
        showNote()

        buttonDelete.setOnClickListener {
            deleteNote()
        }

        textName.doAfterTextChanged { editable ->
            editNoteName()
        }
        textNote.doAfterTextChanged { editable ->
            editNoteText()
        }
    }

    private fun showNote() {
        lifecycleScope.launch{
            val info = notesDao.getNoteById(currentId)
            textName.setText(info?.name)
            textNote.setText(info?.text)
        }
    }

    private fun editNoteName() {
        val nameString = textName.text.toString().trim()

        lifecycleScope.launch{
            notesDao.editNoteNameById(ChangeNoteName(currentId, nameString))
        }
    }

    private fun editNoteText() {
        val textString = textNote.text.toString().trim()

        lifecycleScope.launch{
            notesDao.editNoteTextById(ChangeNoteText(currentId, textString))
        }
    }

    private fun deleteNote(){
        lifecycleScope.launch{
            notesDao.deleteNoteById(currentId)
            finish()
        }
    }

    private fun inicializeInterface(){
        Repositories.initialize(this)
        notesDao = Repositories.noteDao()

        textName = findViewById(R.id.textName)
        textNote = findViewById(R.id.textNote)
        currentId = intent.getIntExtra("NOTE_ID", -1)
        buttonDelete = findViewById(R.id.buttonDelete)
    }
}



