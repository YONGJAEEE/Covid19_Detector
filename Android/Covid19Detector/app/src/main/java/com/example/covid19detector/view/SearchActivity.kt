package com.example.covid19detector.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.example.covid19detector.R
import com.example.covid19detector.adapter.JusoAdapter
import com.example.covid19detector.model.Documents
import com.example.covid19detector.model.JusoResponse
import com.example.covid19detector.network.JusoRetrofitClient
import kotlinx.android.synthetic.main.activity_search.*
import retrofit2.Call
import retrofit2.Response

class SearchActivity : AppCompatActivity() {
    private val APIKey = "KakaoAK 1e0abb48f975bdcc91c3edeafa42dad7"
    var adressList = ArrayList<Documents>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        et_adress.addTextChangedListener(textWatcher)
    }
    private val textWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            getJusoByText(et_adress.text.toString())
        }
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    }
    fun getJusoByText(text : String){
        val call : Call<JusoResponse> = JusoRetrofitClient.instance.GetData.getXYByJuso(APIKey,1,20,text)

        call.enqueue(object : retrofit2.Callback<JusoResponse> {
            override fun onResponse(call: Call<JusoResponse>, response: Response<JusoResponse>) {
                Log.d("Success", response.body().toString())
                if (response.body()?.documents!=null){
                    adressList = response.body()?.documents as ArrayList<Documents>
                    rv_adress.adapter = JusoAdapter(adressList)
                }
            }

            override fun onFailure(call: Call<JusoResponse>, t: Throwable) {
                Log.d("Fail", t.toString())
            }
        })
    }

}