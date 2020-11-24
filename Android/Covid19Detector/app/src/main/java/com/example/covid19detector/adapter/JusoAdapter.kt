package com.example.covid19detector.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19detector.R
import com.example.covid19detector.model.Documents
import com.example.covid19detector.view.MainActivity

class JusoAdapter(val AdressList : ArrayList<Documents>) : RecyclerView.Adapter<JusoAdapter.Holder>(){
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val jusoText = itemView.findViewById<TextView>(R.id.tv_juso)

        fun bind(juso : Documents) {
            jusoText.text = juso.address_name
            itemView.setOnClickListener{
                (itemView.context as Activity).finish()
                val intent = Intent(itemView.context,MainActivity::class.java)
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