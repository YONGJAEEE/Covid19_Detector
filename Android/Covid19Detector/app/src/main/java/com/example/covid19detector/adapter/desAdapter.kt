package com.example.covid19detector.adapter

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19detector.R
import com.example.covid19detector.model.Schedule

class desAdapter (val desList: ArrayList<Schedule>) : RecyclerView.Adapter<desAdapter.Holder>() {
    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val place = itemView.findViewById<TextView>(R.id.place)
        val date = itemView.findViewById<TextView>(R.id.date)
        val time = itemView.findViewById<TextView>(R.id.time)
        fun bind(schedule: Schedule) {
            place.text = schedule.pl.toString()
            date.ScName = schedule.school_name.toString()
            time.text = schedule.school_locate

            itemView.setOnClickListener {
                val builder = AlertDialog.Builder(
                    ContextThemeWrapper(
                        itemView.context,
                        R.style.Theme_AppCompat_Light_Dialog
                    )
                )
                builder.setTitle("학교 선택")
                builder.setMessage("${school.school_name.toString()}으로 선택하시겠습니까?")

                builder.setNegativeButton("취소") { _, _ ->
                    Toast.makeText(itemView.context, "취소되었습니다.", Toast.LENGTH_SHORT).show()
                }
                builder.setPositiveButton("확인") { _, _ ->
                    MyApplication.prefs.setString("schoolName",school.school_name.toString())
                    MyApplication.prefs.setString("officeCode",school.office_code.toString())
                    MyApplication.prefs.setString("schoolId",school.school_id.toString())

                    var intent = Intent(itemView.context,MainActivity::class.java)
                    itemView.context.startActivity(intent)
                    Toast.makeText(itemView.context, "학교 설정을 완료했어요.", Toast.LENGTH_SHORT).show()
                }
                builder.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sclistitem, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return memoList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(memoList[position])
    }

}

