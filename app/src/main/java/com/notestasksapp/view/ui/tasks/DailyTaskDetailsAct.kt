package com.notestasksapp.view.ui.tasks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.notestasksapp.R
import com.notestasksapp.database.DBHandler
import com.notestasksapp.helper.Helper
import kotlinx.android.synthetic.main.act_daily_task_details.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class DailyTaskDetailsAct : AppCompatActivity() {

    private lateinit var dbHandler: DBHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_daily_task_details)

        initialization()

        finish_btn.setOnClickListener {
            Helper.selected_task!!.finished = 1
            dbHandler.updateTask(Helper.selected_task)
            onBackPressed()
        }

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

        if (Helper.selected_task!!.finished == 0){
            val calendar = Calendar.getInstance()
            if (calendar.timeInMillis > endDate.timeInMillis){
                hours_txt.text = "0"
                minutes_txt.text = "0"
            }else{
                hours_txt.text = getRemainingTime(endDate.timeInMillis, 1)
                minutes_txt.text = getRemainingTime(endDate.timeInMillis, 2)
            }
            finish_btn.alpha = 1F
            finish_btn.isEnabled = true
        }else{
            hours_txt.text = "/"
            minutes_txt.text = "/"
            finish_btn.alpha = 0.5F
            finish_btn.isEnabled = false
        }

        task_title_txt.text = Helper.selected_task!!.title
        task_description_txt.text = Helper.selected_task!!.description
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
        val minutes = (totalMinutes % 60).toInt()

        if (type == 1) return "$hours"
        else return "$minutes"
    }

}