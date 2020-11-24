package com.example.covid19detector.view

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.covid19detector.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        tv_adress.setOnClickListener(){
            btnClick()
        }
        btn_search.setOnClickListener(){
            btnClick()
        }
    }
    fun btnClick(){
        val intent = Intent(this,SearchActivity::class.java)
        startActivity(intent)
    }
}
