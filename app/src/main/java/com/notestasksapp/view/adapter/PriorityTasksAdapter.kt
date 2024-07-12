package com.notestasksapp.view.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.helper.Helper
import com.notestasksapp.model.Note
import com.notestasksapp.model.Task
import com.notestasksapp.model.TodoItem
import com.notestasksapp.view.ui.notes.EditNoteAct
import com.notestasksapp.view.ui.tasks.TaskDetailsAct

class PriorityTasksAdapter(val context: Context, var data: ArrayList<Task>, var dbHandler: DBHandler) :
    RecyclerView.Adapter<PriorityTasksAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout._row_priority_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val pos = position

        holder.task_title_txt.text = data[pos].title
        holder.task_description_txt.text = data[pos].description
        holder.task_remaining_time_txt.text = "x days"
        holder.task_progress_txt.text = "80%"
        holder.task_progress.progress = 80

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

}

