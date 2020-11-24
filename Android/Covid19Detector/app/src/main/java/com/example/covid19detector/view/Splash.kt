package com.example.covid19detector.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
    
    private fun startLoading() {
        val handler = Handler()
        handler.postDelayed(Runnable { finish() }, 2000)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
