package com.example.weatherapp

data class WeatherResponse(
    val main:Main,
    val name:String
)

data class Main(
    val temp:Double,
    val humidity :Int
)
