package com.notestasksapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.notestasksapp.R
import com.notestasksapp.view.ui.frags.NotesFrag
import com.notestasksapp.view.ui.frags.StatisticsFrag
import com.notestasksapp.view.ui.frags.TasksFrag
import kotlinx.android.synthetic.main.act_main.*

class MainAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_main)

        initialization()

        tasks_btn.setOnClickListener {
            tasks_img.setImageResource(R.drawable.tasks_in)
            notes_img.setImageResource(R.drawable.notes_out)
            statistics_img.setImageResource(R.drawable.statistics_out)

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, TasksFrag()).commit()
        }

        notes_btn.setOnClickListener {
            tasks_img.setImageResource(R.drawable.tasks_out)
            notes_img.setImageResource(R.drawable.notes_in)
            statistics_img.setImageResource(R.drawable.statistics_out)

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, NotesFrag()).commit()
        }

        statistics_btn.setOnClickListener {
            tasks_img.setImageResource(R.drawable.tasks_out)
            notes_img.setImageResource(R.drawable.notes_out)
            statistics_img.setImageResource(R.drawable.statistics_in)

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, StatisticsFrag()).commit()
        }

    }

    private fun initialization() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, TasksFrag()).commit()
    }

    override fun onBackPressed() {
        tasks_img.setImageResource(R.drawable.tasks_in)
        notes_img.setImageResource(R.drawable.notes_out)
        statistics_img.setImageResource(R.drawable.statistics_out)

        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, TasksFrag()).commit()
    }

}