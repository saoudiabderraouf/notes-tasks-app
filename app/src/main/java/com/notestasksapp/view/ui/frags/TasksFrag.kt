package com.notestasksapp.view.ui.frags

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.view.ui.tasks.AddTaskAct
import com.notestasksapp.view.ui.tasks.CalendarAct
import com.notestasksapp.view.ui.tasks.NotificationsAct
import com.notestasksapp.view.ui.tasks.SearchTaskAct
import kotlinx.android.synthetic.main.frag_tasks.view.*

class TasksFrag : Fragment() {

    private lateinit var mView: View
    private lateinit var dbHandler: DBHandler

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.frag_tasks, container, false)

        initialization()

        mView.search_btn.setOnClickListener {
            startActivity(Intent(requireContext(), SearchTaskAct::class.java))
        }

        mView.calendar_btn.setOnClickListener {
            startActivity(Intent(requireContext(), CalendarAct::class.java))
        }

        mView.notifications_btn.setOnClickListener {
            startActivity(Intent(requireContext(), NotificationsAct::class.java))
        }

        mView.add_task_btn.setOnClickListener {
            startActivity(Intent(requireContext(), AddTaskAct::class.java))
        }

        mView.empty_priority_tasks_list_layout.setOnClickListener {
            startActivity(Intent(requireContext(), AddTaskAct::class.java))
        }

        mView.empty_daily_tasks_list_layout.setOnClickListener {
            startActivity(Intent(requireContext(), AddTaskAct::class.java))
        }

        return mView
    }

    private fun initialization() {
        dbHandler = DBHandler(requireContext())
    }

}