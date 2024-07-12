package com.notestasksapp.view.ui.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.model.Note
import com.notestasksapp.view.adapter.NotesAdapter
import kotlinx.android.synthetic.main.act_search_note.*

class SearchNoteAct : AppCompatActivity() {

    private lateinit var dbHandler: DBHandler
    private lateinit var notes: ArrayList<Note>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_search_note)

        initialization()

        search_edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (search_edt.text.isNullOrEmpty()){
                    notes_list.adapter = NotesAdapter(this@SearchNoteAct, notes, dbHandler)
                }else{
                    notes_list.adapter = NotesAdapter(this@SearchNoteAct, ArrayList(), dbHandler)

                    val filteredNotes = ArrayList<Note>()
                    filteredNotes.addAll(notes.filter { note -> note.title.toLowerCase().contains(search_edt.text.toString().toLowerCase())
                            || note.description.toLowerCase().contains(search_edt.text.toString().toLowerCase()) })

                    notes_list.adapter = NotesAdapter(this@SearchNoteAct, filteredNotes, dbHandler)
                }
            }
        })

        clear_btn.setOnClickListener {
            search_edt.setText("")
            notes_list.adapter = NotesAdapter(this@SearchNoteAct, notes, dbHandler)
        }

        cancel_btn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun initialization() {
        dbHandler = DBHandler(this)

        notes_list.layoutManager = GridLayoutManager(this, 2)
        notes_list.setHasFixedSize(true)

        notes_list.adapter = NotesAdapter(this, ArrayList(), dbHandler)

        notes = ArrayList()
        notes.clear()
        notes = dbHandler.notes

        notes_list.adapter = NotesAdapter(this, notes, dbHandler)

    }

}