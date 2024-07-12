package com.notestasksapp.view.ui.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.helper.Helper
import com.notestasksapp.model.TodoItem
import com.notestasksapp.view.adapter.PriorityTodoAdapter
import kotlinx.android.synthetic.main.act_priority_task_details.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class PriorityTaskDetailsAct : AppCompatActivity() {

    private lateinit var dbHandler: DBHandler
    private lateinit var todoItems: ArrayList<TodoItem>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_priority_task_details)

        initialization()

        back_btn.setOnClickListener {
            onBackPressed()
        }

    }

    private fun initialization() {
        dbHandler = DBHandler(this)

        initData()
    }

    private fun initData() {
        val startDay = Helper.selected_task!!.start_date.substringBefore(".").toInt()
        val startMonth = Helper.selected_task!!.start_date.substringBeforeLast(".").substringAfter(".").toInt()
        val startYear = Helper.selected_task!!.start_date.substringAfterLast(".").toInt()
        val startDate = Calendar.getInstance()
        startDate.set(startYear, startMonth - 1, startDay, 23, 59, 59)
        start_date_txt.text = SimpleDateFormat("dd MMMM yyyy").format(startDate.timeInMillis)


        val endDay = Helper.selected_task!!.end_date.substringBefore(".").toInt()
        val endMonth = Helper.selected_task!!.end_date.substringBeforeLast(".").substringAfter(".").toInt()
        val endYear = Helper.selected_task!!.end_date.substringAfterLast(".").toInt()
        val endDate = Calendar.getInstance()
        endDate.set(endYear, endMonth - 1, endDay, 23, 59, 59)
        end_date_txt.text = SimpleDateFormat("dd MMMM yyyy").format(endDate.timeInMillis)

        val calendar = Calendar.getInstance()
        if (calendar.timeInMillis > endDate.timeInMillis){
            months_txt.text = "0"
            days_txt.text = "0"
            hours_txt.text = "0"
        }else{
            months_txt.text = getRemainingTime(endDate.timeInMillis, 1)
            days_txt.text = getRemainingTime(endDate.timeInMillis, 2)
            hours_txt.text = getRemainingTime(endDate.timeInMillis, 3)
        }

        task_title_txt.text = Helper.selected_task!!.title
        task_description_txt.text = Helper.selected_task!!.description

        updateTodoItemsList()
    }

    private fun updateTodoItemsList() {
        todo_list.layoutManager = LinearLayoutManager(this)
        todo_list.setHasFixedSize(true)

        todo_list.adapter = PriorityTodoAdapter(this, ArrayList(), dbHandler)

        todoItems = ArrayList()
        todoItems.clear()
        todoItems = dbHandler.todos.filter { todoItem -> todoItem.task == Helper.selected_task!!.id } as ArrayList<TodoItem>

        todo_list.adapter = PriorityTodoAdapter(this, todoItems, dbHandler)
        updateProgress()
    }

    fun updateProgress() {
        val allItems = todoItems.size
        val finishedItems = (todoItems.filter { todoItem -> todoItem.finished == 1 } as ArrayList<TodoItem>).size
        val progress = (100 * finishedItems / allItems)

        task_progress_txt.text = "$progress%"
        task_progress.progress = progress
    }

    private fun getRemainingTime(targetTimeInMillis: Long, type: Int): String {
        val currentTimeInMillis = System.currentTimeMillis()
        val diffInMillis = targetTimeInMillis - currentTimeInMillis

        // Convert milliseconds to days, hours, and minutes
        val totalDays = TimeUnit.MILLISECONDS.toDays(diffInMillis)
        val totalHours = TimeUnit.MILLISECONDS.toHours(diffInMillis)
        val totalMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillis)

        // Calculate remaining months, days, and hours
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeInMillis
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)

        calendar.timeInMillis = targetTimeInMillis
        val targetMonth = calendar.get(Calendar.MONTH)
        val targetDay = calendar.get(Calendar.DAY_OF_MONTH)
        val targetHour = calendar.get(Calendar.HOUR_OF_DAY)

        val months = targetMonth - currentMonth + (if (targetDay >= currentDay) 0 else -1)
        val days = targetDay - currentDay + (if (targetHour >= currentHour) 0 else -1)
        val hours = (totalHours % 24).toInt()

        if (type == 1) return "$months"
        else if (type == 2) return "$days"
        else return "$hours"
    }

}