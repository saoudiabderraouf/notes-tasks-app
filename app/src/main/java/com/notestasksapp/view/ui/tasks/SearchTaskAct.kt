package com.notestasksapp.view.ui.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.recyclerview.widget.GridLayoutManager
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.model.Task
import com.notestasksapp.view.adapter.TasksAdapter
import kotlinx.android.synthetic.main.act_search_task.*

class SearchTaskAct : AppCompatActivity() {

    private lateinit var dbHandler: DBHandler
    private lateinit var tasks: ArrayList<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_search_task)

        initialization()

        search_edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (search_edt.text.isNullOrEmpty()){
                    tasks_list.adapter = TasksAdapter(this@SearchTaskAct, tasks, dbHandler)
                }else{
                    tasks_list.adapter = TasksAdapter(this@SearchTaskAct, ArrayList(), dbHandler)

                    val filteredTasks = ArrayList<Task>()
                    filteredTasks.addAll(tasks.filter { task -> task.title.toLowerCase().contains(search_edt.text.toString().toLowerCase())
                            || task.description.toLowerCase().contains(search_edt.text.toString().toLowerCase()) })

                    tasks_list.adapter = TasksAdapter(this@SearchTaskAct, filteredTasks, dbHandler)
                }
            }
        })

        clear_btn.setOnClickListener {
            search_edt.setText("")
            tasks_list.adapter = TasksAdapter(this@SearchTaskAct, tasks, dbHandler)
        }

        cancel_btn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun initialization() {
        dbHandler = DBHandler(this)

        tasks_list.layoutManager = GridLayoutManager(this, 2)
        tasks_list.setHasFixedSize(true)

        tasks_list.adapter = TasksAdapter(this, ArrayList(), dbHandler)

        tasks = ArrayList()
        tasks.clear()
        tasks = dbHandler.tasks

        tasks_list.adapter = TasksAdapter(this, tasks, dbHandler)

    }

}