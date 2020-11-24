package com.example.covid19detector.view

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.covid19detector.R
import com.example.covid19detector.network.ScheduleDatabase
import kotlinx.android.synthetic.main.activity_schedule.*
import java.text.SimpleDateFormat
import java.util.*


class ScheduleActivity : AppCompatActivity() {
    private var scheduleDB : ScheduleDatabase? = null
    private var callbackMethod: DatePickerDialog.OnDateSetListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        date_picker_actions.text = SimpleDateFormat("yyyy년 MM월 dd일").format(System.currentTimeMillis())

        var cal = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, monthOfYear)
            cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val myFormat = "yyyy년 MM월 dd일" // mention the format you need
            val sdf = SimpleDateFormat(myFormat, Locale.KOREA)
            date_picker_actions.text = sdf.format(cal.time)

        }

        date_picker_actions.setOnClickListener {
            DatePickerDialog(this@ScheduleActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)).show()
        }


        scheduleDB = ScheduleDatabase.getInstance(this)

    }


}