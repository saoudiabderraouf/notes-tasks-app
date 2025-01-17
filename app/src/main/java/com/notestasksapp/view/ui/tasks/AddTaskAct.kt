package com.notestasksapp.view.ui.tasks

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.model.Task
import com.notestasksapp.model.TodoItem
import com.notestasksapp.view.adapter.TodoAdapter
import com.notestasksapp.view.dialog.ConfirmationDialog
import kotlinx.android.synthetic.main.act_add_task.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddTaskAct : AppCompatActivity() {

    private lateinit var dbHandler: DBHandler
    private lateinit var todoItems: ArrayList<String>
    private lateinit var calendar: Calendar

    private var startDate = ""
    private var endDate = ""
    private var category = "Priority task"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_add_task)

        initialization()

        start_date_btn.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                startDate = SimpleDateFormat("dd.MM.yyyy").format(selectedDate.time)
                start_date_txt.text = SimpleDateFormat("dd MMMM yyyy").format(selectedDate.time)
            },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        end_date_btn.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this, { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                endDate = SimpleDateFormat("dd.MM.yyyy").format(selectedDate.time)
                end_date_txt.text = SimpleDateFormat("dd MMMM yyyy").format(selectedDate.time)
            },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        priority_tasks_btn.setOnClickListener {
            category = "Priority task"

            priority_tasks_btn.setBackgroundResource(R.drawable._rounded_blue_10dp)
            daily_tasks_btn.setBackgroundResource(R.drawable._rounded_white_10dp_1dp)

            priority_tasks_txt.setTextColor(Color.parseColor("#FFFFFF"))
            daily_tasks_txt.setTextColor(Color.parseColor("#006EE9"))

            todo_items_layout.visibility = View.VISIBLE
        }

        daily_tasks_btn.setOnClickListener {
            category = "Daily task"

            priority_tasks_btn.setBackgroundResource(R.drawable._rounded_white_10dp_1dp)
            daily_tasks_btn.setBackgroundResource(R.drawable._rounded_blue_10dp)

            priority_tasks_txt.setTextColor(Color.parseColor("#006EE9"))
            daily_tasks_txt.setTextColor(Color.parseColor("#FFFFFF"))

            todo_items_layout.visibility = View.GONE
        }

        add_todo_img.setOnClickListener {
            if (!todo_edt.text.isNullOrEmpty()) {
                todo_list.adapter = TodoAdapter(this, ArrayList(), dbHandler)
                todoItems.add(todo_edt.text.toString())
                todo_edt.setText("")
                todo_list.adapter = TodoAdapter(this, todoItems, dbHandler)
            }
        }

        remove_todo_img.setOnClickListener {
            todo_edt.setText("")
        }

        save_btn.setOnClickListener {
            if (verifyData()){
                if (category == "Daily task"){
                    startDate = endDate
                }
                val task = Task(0, task_title_edt.text.toString(), task_description_edt.text.toString(), category, startDate, endDate, 0)
                task.id = dbHandler.addTask(task)
                if (category == "Priority task"){
                    for (item in todoItems){
                        dbHandler.addTodo(TodoItem(0, item, 0, task.id))
                    }
                }
                val confirmationDialog = ConfirmationDialog(this, "Add a new task", "Do you want to add a new task?")
                confirmationDialog.ok_btn.setOnClickListener {
                    initData()
                    confirmationDialog.dismiss()
                }
                confirmationDialog.cancel_btn.setOnClickListener {
                    confirmationDialog.dismiss()
                    onBackPressed()
                }
                confirmationDialog.show()
            }
        }

        back_btn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun initialization() {
        dbHandler = DBHandler(this)

        calendar = Calendar.getInstance()
        todoItems = ArrayList()

        todo_list.layoutManager = LinearLayoutManager(this)
        todo_list.setHasFixedSize(true)
        todo_list.adapter = TodoAdapter(this, ArrayList(), dbHandler)
    }

    private fun initData() {
        startDate = ""
        endDate = ""
        category = "Priority task"
        start_date_txt.text = "xx Xxxxxxx xxxx"
        end_date_txt.text = "xx Xxxxxxx xxxx"
        task_title_edt.setText("")
        task_description_edt.setText("")
        todo_edt.setText("")
        todoItems.clear()
        todo_list.adapter = TodoAdapter(this, ArrayList(), dbHandler)

        priority_tasks_btn.setBackgroundResource(R.drawable._rounded_blue_10dp)
        daily_tasks_btn.setBackgroundResource(R.drawable._rounded_white_10dp_1dp)

        priority_tasks_txt.setTextColor(Color.parseColor("#FFFFFF"))
        daily_tasks_txt.setTextColor(Color.parseColor("#006EE9"))

        todo_items_layout.visibility = View.VISIBLE
    }

    private fun verifyData(): Boolean {
        if (startDate == ""){
            Toast.makeText(this, "Please, select a start date first!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (endDate == ""){
            Toast.makeText(this, "Please, select an end date first!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (startDate > endDate){
            Toast.makeText(this, "End date can't be before the start date!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (task_title_edt.text.isNullOrEmpty()) {
            task_title_edt.error = "Field can't be empty!"
            return false
        }

        if (task_description_edt.text.isNullOrEmpty()) {
            task_description_edt.error = "Field can't be empty!"
            return false
        }

        if (category == "Priority task" && todoItems.isNullOrEmpty()){
            Toast.makeText(this, "Please, add at least one todo item!", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

}