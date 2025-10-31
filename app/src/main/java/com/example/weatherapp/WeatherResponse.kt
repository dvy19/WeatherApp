package com.example.weatherapp

data class WeatherResponse(
    val name: String,           // City name
    val main: Main,             // Main weather data
    val weather: List<Weather>, // Weather conditions
    val wind: Wind,             // Wind information
    val clouds: Clouds,         // Cloud information
    val rain: Rain? = null,     // Rain data (optional)

)

data class Main(
    val temp: Double,           // Temperature
    val feels_like: Double,     // Feels like temperature
    val pressure: Int,          // Atmospheric pressure
    val humidity: Int,          // Humidity %
    val temp_min: Double,       // Minimum temperature
    val temp_max: Double        // Maximum temperature
)

data class Weather(
    val main: String,           // Weather main (Rain, Snow, Clouds, etc.)
    val description: String,    // Weather description
    val icon: String           // Weather icon code
)

data class Wind(
    val speed: Double,          // Wind speed
    val deg: Int               // Wind direction in degrees
)

data class Clouds(
    val all: Int               // Cloudiness %
)

data class Rain(
    val `1h`: Double? = null,  // Rain volume for last 1 hour (mm)
    val `3h`: Double? = null   // Rain volume for last 3 hours (mm)
)