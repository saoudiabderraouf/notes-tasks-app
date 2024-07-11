package com.notestasksapp.view.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.helper.Helper
import com.notestasksapp.model.Note
import com.notestasksapp.view.ui.notes.NoteDetailsAct

class NotesAdapter(val context: Context, var data: ArrayList<Note>, dbHandler: DBHandler) :
    RecyclerView.Adapter<NotesAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(R.layout._row_note, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val pos = position

        holder.note_title_txt.text = data[pos].title
        holder.note_description_txt.text = data[pos].description
        holder.note_date_txt.text = data[pos].last_updated

        holder.itemView.setOnClickListener {
            Helper.selected_note = data[pos]
            context.startActivity(Intent(context, NoteDetailsAct::class.java))
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val note_title_txt = itemView.findViewById<TextView>(R.id.note_title_txt)
        val note_description_txt = itemView.findViewById<TextView>(R.id.note_description_txt)
        val note_date_txt = itemView.findViewById<TextView>(R.id.note_date_txt)
    }

}

