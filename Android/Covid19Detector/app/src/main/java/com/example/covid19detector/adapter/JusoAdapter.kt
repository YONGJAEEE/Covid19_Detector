package com.example.covid19detector.adapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19detector.R
import com.example.covid19detector.model.Documents
import com.example.covid19detector.model.LatlngData
import com.example.covid19detector.view.MainActivity
import com.example.covid19detector.widget.DataUtil

class JusoAdapter(val AdressList : ArrayList<Documents>) : RecyclerView.Adapter<JusoAdapter.Holder>(){
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val jusoText = itemView.findViewById<TextView>(R.id.tv_juso)

        fun bind(juso : Documents) {
            jusoText.text = juso.address_name
            itemView.setOnClickListener{
                Log.d("TAGTAG","위도 : "+juso.y+"  경도 : "+juso.x)
                val latlngData = LatlngData(juso.y!!.toDouble(),juso.x!!.toDouble())
                DataUtil.LatLngData.value = latlngData
                (itemView.context as Activity).finish()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adressitem, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(AdressList[position])
    }

    override fun getItemCount(): Int {
        return AdressList.size
    }
}