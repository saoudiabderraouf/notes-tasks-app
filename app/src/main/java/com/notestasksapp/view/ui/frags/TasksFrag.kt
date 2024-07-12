package com.notestasksapp.view.ui.frags

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.model.Task
import com.notestasksapp.view.adapter.DailyTasksAdapter
import com.notestasksapp.view.adapter.PriorityTasksAdapter
import com.notestasksapp.view.ui.tasks.AddTaskAct
import com.notestasksapp.view.ui.tasks.CalendarAct
import com.notestasksapp.view.ui.tasks.NotificationsAct
import com.notestasksapp.view.ui.tasks.SearchTaskAct
import kotlinx.android.synthetic.main.frag_tasks.view.*

class TasksFrag : Fragment() {

    private lateinit var mView: View
    private lateinit var dbHandler: DBHandler
    private lateinit var dailyTasks: ArrayList<Task>
    private lateinit var priorityTasks: ArrayList<Task>

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

        mView.priority_tasks_list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mView.priority_tasks_list.setHasFixedSize(true)

        priorityTasks = ArrayList()
        priorityTasks.clear()
        priorityTasks = dbHandler.tasks.filter { task -> task.category == "Priority task" } as ArrayList<Task>

        if (priorityTasks.isEmpty()){
            mView.empty_priority_tasks_list_layout.visibility = View.VISIBLE
            mView.priority_tasks_list.visibility = View.GONE
        }else{
            mView.empty_priority_tasks_list_layout.visibility = View.GONE
            mView.priority_tasks_list.visibility = View.VISIBLE
            mView.priority_tasks_list.adapter = PriorityTasksAdapter(requireContext(), priorityTasks.reverse(), dbHandler)
        }

        mView.daily_tasks_list.layoutManager = LinearLayoutManager(requireContext())
        mView.daily_tasks_list.setHasFixedSize(true)

        dailyTasks = ArrayList()
        dailyTasks.clear()
        dailyTasks = dbHandler.tasks.filter { task -> task.category == "Daily task" } as ArrayList<Task>

        if (dailyTasks.isEmpty()){
            mView.empty_daily_tasks_list_layout.visibility = View.VISIBLE
            mView.daily_tasks_list.visibility = View.GONE
        }else{
            mView.empty_daily_tasks_list_layout.visibility = View.GONE
            mView.daily_tasks_list.visibility = View.VISIBLE
            mView.daily_tasks_list.adapter = DailyTasksAdapter(requireContext(), dailyTasks.reverse(), dbHandler)
        }
    }

    override fun onResume() {
        super.onResume()

        mView.priority_tasks_list.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        mView.priority_tasks_list.setHasFixedSize(true)

        priorityTasks = ArrayList()
        priorityTasks.clear()
        priorityTasks = dbHandler.tasks.filter { task -> task.category == "Priority task" } as ArrayList<Task>

        if (priorityTasks.isEmpty()){
            mView.empty_priority_tasks_list_layout.visibility = View.VISIBLE
            mView.priority_tasks_list.visibility = View.GONE
        }else{
            mView.empty_priority_tasks_list_layout.visibility = View.GONE
            mView.priority_tasks_list.visibility = View.VISIBLE
            mView.priority_tasks_list.adapter = PriorityTasksAdapter(requireContext(), priorityTasks.reverse(), dbHandler)
        }

        mView.daily_tasks_list.adapter = DailyTasksAdapter(requireContext(), ArrayList(), dbHandler)

        dailyTasks = ArrayList()
        dailyTasks.clear()
        dailyTasks = dbHandler.tasks.filter { task -> task.category == "Daily task" } as ArrayList<Task>

        if (dailyTasks.isEmpty()){
            mView.empty_daily_tasks_list_layout.visibility = View.VISIBLE
            mView.daily_tasks_list.visibility = View.GONE
        }else{
            mView.empty_daily_tasks_list_layout.visibility = View.GONE
            mView.daily_tasks_list.visibility = View.VISIBLE
            mView.daily_tasks_list.adapter = DailyTasksAdapter(requireContext(), dailyTasks.reverse(), dbHandler)
        }
    }

}