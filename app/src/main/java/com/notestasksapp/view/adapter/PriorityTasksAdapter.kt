package com.notestasksapp.view.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.helper.Helper
import com.notestasksapp.model.Note
import com.notestasksapp.model.Task
import com.notestasksapp.model.TodoItem
import com.notestasksapp.view.ui.notes.EditNoteAct
import com.notestasksapp.view.ui.tasks.CalendarAct
import com.notestasksapp.view.ui.tasks.TaskDetailsAct
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class PriorityTasksAdapter(val context: Context, var data: ArrayList<Task>, var dbHandler: DBHandler) :
    RecyclerView.Adapter<PriorityTasksAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout._row_priority_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val pos = position
        val todoItems = dbHandler.todos.filter { todoItem -> todoItem.task == data[pos].id } as ArrayList<TodoItem>
        val allItems = todoItems.size
        val finishedItems = (todoItems.filter { todoItem -> todoItem.finished == 1 } as ArrayList<TodoItem>).size
        val progress = (100 * finishedItems / allItems)
        val calendar = Calendar.getInstance()
        val day = data[pos].end_date.substringBefore(".").toInt()
        val month = data[pos].end_date.substringBeforeLast(".").substringAfter(".").toInt()
        val year = data[pos].end_date.substringAfterLast(".").toInt()
        val endDate = Calendar.getInstance()
        endDate.set(year, month - 1, day, 23, 59, 59)

        holder.task_title_txt.text = data[pos].title
        holder.task_description_txt.text = data[pos].description
        holder.task_remaining_time_txt.text = "x days"
        holder.task_progress_txt.text = "$progress%"
        holder.task_progress.progress = progress

        if (calendar.timeInMillis > endDate.timeInMillis){
            holder.task_remaining_time_txt.text = "end"
        }else{
            holder.task_remaining_time_txt.text = getRemainingTime(endDate.timeInMillis)
        }

        val cardView = holder.itemView as CardView
        if (pos % 4 == 0){
            cardView.setCardBackgroundColor(Color.parseColor("#006EE9"))
        }else if (pos % 4 == 1){
            cardView.setCardBackgroundColor(Color.parseColor("#311F65"))
        }else if (pos % 4 == 2){
            cardView.setCardBackgroundColor(Color.parseColor("#D92C2C"))
        }else{
            cardView.setCardBackgroundColor(Color.parseColor("#1D6820"))
        }

        holder.itemView.setOnClickListener {
            Helper.selected_task = data[pos]
            context.startActivity(Intent(context, TaskDetailsAct::class.java))
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val task_title_txt = itemView.findViewById<TextView>(R.id.task_title_txt)
        val task_description_txt = itemView.findViewById<TextView>(R.id.task_description_txt)
        val task_remaining_time_txt = itemView.findViewById<TextView>(R.id.task_remaining_time_txt)
        val task_progress_txt = itemView.findViewById<TextView>(R.id.task_progress_txt)
        val task_progress = itemView.findViewById<LinearProgressIndicator>(R.id.task_progress)
    }

    private fun getRemainingTime(targetTimeInMillis: Long): String {
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

        if (months > 0) return "$months months"
        else if (days > 0) return "$days days"
        else return "$hours hours"
    }

}

