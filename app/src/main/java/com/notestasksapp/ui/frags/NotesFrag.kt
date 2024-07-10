package com.notestasksapp.ui.frags

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.notestasksapp.R
import com.notestasksapp.ui.notes.AddNoteAct
import com.notestasksapp.ui.notes.NotesNotificationsAct
import com.notestasksapp.ui.notes.SearchNoteAct
import kotlinx.android.synthetic.main.frag_notes.view.*

class NotesFrag : Fragment() {

    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.frag_notes, container, false)

        initialization()

        mView.search_btn.setOnClickListener {
            startActivity(Intent(requireContext(), SearchNoteAct::class.java))
        }

        mView.notifications_btn.setOnClickListener {
            startActivity(Intent(requireContext(), NotesNotificationsAct::class.java))
        }

        mView.add_note_btn.setOnClickListener {
            startActivity(Intent(requireContext(), AddNoteAct::class.java))
        }

        return mView
    }

    private fun initialization() {
        mView.notes_list.layoutManager = LinearLayoutManager(requireContext())
        mView.notes_list.setHasFixedSize(true)
    }

}