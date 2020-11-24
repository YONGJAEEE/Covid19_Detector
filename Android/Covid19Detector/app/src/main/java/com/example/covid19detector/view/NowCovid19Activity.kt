package com.example.covid19detector.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.covid19detector.R
import com.example.covid19detector.model.CovidResponse
import com.example.covid19detector.network.ApiRetrofitClient
import com.example.covid19detector.widget.TranslateDate
import kotlinx.android.synthetic.main.activity_now_covid19.*
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

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

        getTotalCovid()
    }

    fun getTotalCovid(){
        val Call : Call<CovidResponse> = ApiRetrofitClient.instance.GetData.getTotalCovid()

        Call.enqueue(object : retrofit2.Callback<CovidResponse> {
            override fun onResponse(call: Call<CovidResponse>, response: Response<CovidResponse>) {
                tv_patient_num.text = response.body()!!.patient
                tv_patient_today.text = response.body()!!.add_patient
                tv_relase_num.text = response.body()!!.release
                tv_relase_today.text = response.body()!!.add_release
                tv_care_num.text = response.body()!!.care
                tv_care_today.text = response.body()!!.add_care
                tv_dead_num.text = response.body()!!.dead
                tv_dead_today.text = response.body()!!.add_dead
            }

            override fun onFailure(call: Call<CovidResponse>, t: Throwable) {
                Log.w("Fail", "서버에서 값을 받아오는데에 실패했어요.")
                Toast.makeText(this@NowCovid19Activity,
                    "값을 읽어오지 못했습니다. 잠시후 다시 시도해주세요.",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }
}