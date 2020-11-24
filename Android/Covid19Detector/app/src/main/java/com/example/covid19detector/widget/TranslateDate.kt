package com.example.covid19detector.widget

class TranslateDate {
    fun translateDate(dateNum : Int) : String{
        when(dateNum){
            1 -> return "일요일"
            2 -> return "월요일"
            3 -> return "화요일"
            4 -> return "수요일"
            5 -> return "목요일"
            6 -> return "금요일"
            7 -> return "토요일"
            else -> return "null"
        }
    }
}