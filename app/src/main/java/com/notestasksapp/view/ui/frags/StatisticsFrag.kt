package com.notestasksapp.view.ui.frags

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.model.Note
import com.notestasksapp.model.Task
import kotlinx.android.synthetic.main.frag_statistics.view.*

class StatisticsFrag : Fragment() {

    private lateinit var mView: View
    private lateinit var dbHandler: DBHandler
    private lateinit var allNotes: ArrayList<Note>
    private lateinit var allTasks: ArrayList<Task>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.frag_statistics, container, false)

        initialization()

        mView.notifications_btn.setOnClickListener {
            Toast.makeText(requireContext(), "Not implemented yet!", Toast.LENGTH_SHORT).show()
        }

        return mView
    }

    private fun initialization() {
        dbHandler = DBHandler(requireContext())

        allTasks = ArrayList()
        allTasks.clear()
        allTasks = dbHandler.tasks
        val finishedTasks = allTasks.filter { task -> task.finished == 1} as ArrayList<Task>
        mView.total_tasks_txt.text = allTasks.size.toString()
        mView.completed_tasks_txt.text = finishedTasks.size.toString()

        allNotes = ArrayList()
        allNotes.clear()
        allNotes = dbHandler.notes
        mView.total_notes_txt.text = allNotes.size.toString()
    }

}