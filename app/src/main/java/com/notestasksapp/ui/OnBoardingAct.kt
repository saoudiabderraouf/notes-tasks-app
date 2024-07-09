package com.notestasksapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.notestasksapp.R
import com.notestasksapp.model.OnBoardingItem
import kotlinx.android.synthetic.main.act_on_boarding.*

class OnBoardingAct : AppCompatActivity() {

    private lateinit var onBoardingItems: ArrayList<OnBoardingItem>
    private lateinit var dots: ArrayList<View>

    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_on_boarding)

        initialization()

        skip_btn.setOnClickListener {
            startActivity(Intent(this, MainAct::class.java))
            finish()
        }

        next_start_btn.setOnClickListener {
            position ++
            updateUi()
        }

    }

    private fun initialization() {
        onBoardingItems = ArrayList()
        onBoardingItems.add(OnBoardingItem("Easy Time Management", "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first", R.drawable.img_1))
        onBoardingItems.add(OnBoardingItem("Increase Work Effectiveness", "Time management and the determination of more important tasks will give your job statistics better and always improve", R.drawable.img_2))
        onBoardingItems.add(OnBoardingItem("Reminder Notification", "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments well and according to the time you have set", R.drawable.img_3))

        dots = ArrayList()
        dots.add(circle_1)
        dots.add(circle_2)
        dots.add(circle_3)
    }

    private fun updateUi() {
        if (position > 2){
            startActivity(Intent(this, MainAct::class.java))
            finish()
        }else{
            if (position == 2){
                next_start_txt.text = "Get Started"
            }else{
                next_start_txt.text = "Next"
            }
            for (i in dots.indices){
                dots[i].setBackgroundResource(R.drawable._circle_gray)
            }
            dots[position].setBackgroundResource(R.drawable._circle_blue)
            on_boarding_title.text = onBoardingItems[position].title
            on_boarding_description.text = onBoardingItems[position].description
            on_boarding_img.setImageResource(onBoardingItems[position].image)
        }
    }

}