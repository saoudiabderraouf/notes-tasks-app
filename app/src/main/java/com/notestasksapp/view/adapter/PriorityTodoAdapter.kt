package com.notestasksapp.view.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.helper.Helper
import com.notestasksapp.model.Task
import com.notestasksapp.model.TodoItem
import com.notestasksapp.view.ui.tasks.PriorityTaskDetailsAct

class PriorityTodoAdapter(val context: Context, var data: ArrayList<TodoItem>, var dbHandler: DBHandler) :
    RecyclerView.Adapter<PriorityTodoAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout._row_daily_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val pos = position

        holder.todo_item_title.text = data[pos].title

        if (data[pos].finished == 1){
            holder.todo_item_title.setTextColor(Color.parseColor("#006EE9"))
            holder.todo_item_checkbox.setImageResource(R.drawable.ic_check_box)
        }else{
            holder.todo_item_title.setTextColor(Color.parseColor("#4A4646"))
            holder.todo_item_checkbox.setImageResource(R.drawable.ic_check_box_outline)
        }

        holder.todo_item_checkbox.setOnClickListener {
            if (data[pos].finished == 0){
                holder.todo_item_title.setTextColor(Color.parseColor("#006EE9"))
                holder.todo_item_checkbox.setImageResource(R.drawable.ic_check_box)

                data[pos].finished = 1
                dbHandler.updateTodo(data[pos])
            }else{
                holder.todo_item_title.setTextColor(Color.parseColor("#4A4646"))
                holder.todo_item_checkbox.setImageResource(R.drawable.ic_check_box_outline)

                data[pos].finished = 0
                dbHandler.updateTodo(data[pos])
            }
            (context as PriorityTaskDetailsAct).updateProgress()
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todo_item_title = itemView.findViewById<TextView>(R.id.todo_item_title)
        val todo_item_checkbox = itemView.findViewById<ImageView>(R.id.todo_item_checkbox)
    }

}

