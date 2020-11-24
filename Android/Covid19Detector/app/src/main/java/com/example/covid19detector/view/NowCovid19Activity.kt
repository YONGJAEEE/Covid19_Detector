package com.example.covid19detector.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.covid19detector.R
import com.example.covid19detector.widget.TranslateDate
import kotlinx.android.synthetic.main.activity_now_covid19.*
import org.w3c.dom.Document
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.xml.parsers.DocumentBuilderFactory

@RequiresApi(Build.VERSION_CODES.O)
class NowCovid19Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_now_covid19)

        val todayDate = LocalDate.now()
        val formatedDate = todayDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        val translate = TranslateDate()

        val cal = Calendar.getInstance()

        val dayNum = cal.get(Calendar.DAY_OF_WEEK)

        todayText.setText(formatedDate.toString() + "  " + translate.translateDate(dayNum))
    }
}