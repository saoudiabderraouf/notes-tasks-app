package com.notestasksapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler

class TodoAdapter(val context: Context, var data: ArrayList<String>, var dbHandler: DBHandler) :
    RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout._row_todo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val pos = position

        holder.todo_item_title.text = data[pos]

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todo_item_title = itemView.findViewById<TextView>(R.id.todo_item_title)
    }

}

