package com.example.covid19detector.model

data class PostResponse(
    val locate : String,
    val x : String,
    val y : String,
    val distance : Int
)