package com.notestasksapp.view.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.notestasksapp.R

class SplashAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_splash)

        initialization()

    }

    private fun initialization() {
        setTimer()
    }

    private fun setTimer() {
        val timer = object: CountDownTimer(2000, 100) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                startActivity(Intent(this@SplashAct,OnBoardingAct::class.java))
                finish()
            }
        }
        timer.start()
    }

}