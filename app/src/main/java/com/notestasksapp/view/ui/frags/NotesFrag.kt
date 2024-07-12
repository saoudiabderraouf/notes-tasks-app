package com.notestasksapp.view.ui.frags

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.model.Note
import com.notestasksapp.view.adapter.NotesAdapter
import com.notestasksapp.view.ui.notes.AddNoteAct
import com.notestasksapp.view.ui.notes.SearchNoteAct
import kotlinx.android.synthetic.main.frag_notes.view.*

class NotesFrag : Fragment() {

    private lateinit var mView: View
    private lateinit var dbHandler: DBHandler
    private lateinit var notes: ArrayList<Note>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.frag_notes, container, false)

        initialization()

        mView.search_btn.setOnClickListener {
            startActivity(Intent(requireContext(), SearchNoteAct::class.java))
        }

        mView.notifications_btn.setOnClickListener {
            Toast.makeText(requireContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show()
        }

        mView.add_note_btn.setOnClickListener {
            startActivity(Intent(requireContext(), AddNoteAct::class.java))
        }

        return mView
    }

    private fun initialization() {
        dbHandler = DBHandler(requireContext())

        mView.notes_list.layoutManager = GridLayoutManager(requireContext(), 2)
        mView.notes_list.setHasFixedSize(true)

        notes = ArrayList()
        notes.clear()
        notes = dbHandler.notes
        notes.reverse()

        if (notes.isNotEmpty()){
            mView.notes_list.adapter = NotesAdapter(requireContext(), notes, dbHandler)
            mView.notes_list.visibility = View.VISIBLE
            mView.empty_list_layout.visibility = View.GONE
        }else{
            mView.notes_list.visibility = View.GONE
            mView.empty_list_layout.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()

        mView.notes_list.adapter = NotesAdapter(requireContext(), ArrayList(), dbHandler)

        notes = ArrayList()
        notes.clear()
        notes = dbHandler.notes
        notes.reverse()

        if (notes.isNotEmpty()){
            mView.notes_list.adapter = NotesAdapter(requireContext(), notes, dbHandler)
            mView.notes_list.visibility = View.VISIBLE
            mView.empty_list_layout.visibility = View.GONE
        }else{
            mView.notes_list.visibility = View.GONE
            mView.empty_list_layout.visibility = View.VISIBLE
        }
    }

}