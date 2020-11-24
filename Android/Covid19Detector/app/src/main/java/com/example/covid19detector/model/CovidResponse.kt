package com.example.covid19detector.model

data class CovidResponse (
    val patient : String,
    val release : String,
    val care : String,
    val dead : String,
    val add_patient : String,
    val add_release : String,
    val add_care : String,
    val add_dead : String
)