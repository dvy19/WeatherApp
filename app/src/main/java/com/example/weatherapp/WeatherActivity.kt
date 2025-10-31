package com.example.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ProgressBar
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherActivity : AppCompatActivity() {

    // Declare UI components
    private lateinit var cityEditText: EditText
    private lateinit var cityTextView: TextView
    private lateinit var tempTextView: TextView
    private lateinit var fetchButton: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var errorTextView: TextView

    // Retrofit setup
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val weatherService = retrofit.create(WeatherService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.weather)

        // Initialize UI components
        initializeViews()

        // Set click listener
        fetchButton.setOnClickListener {
            fetchWeather()
        }
    }

    private fun initializeViews() {
        // Initialize all views
        cityEditText = findViewById(R.id.cityEditText)
        cityTextView = findViewById(R.id.cityTextView)
        tempTextView = findViewById(R.id.tempTextView)
        fetchButton = findViewById(R.id.fetchButton)
        progressBar = findViewById(R.id.progressBar)
        errorTextView = findViewById(R.id.errorTextView)
    }

    private fun fetchWeather() {
        // Get city name from user input
        val cityName = cityEditText.text.toString().trim()

        // Validate input
        if (cityName.isEmpty()) {
            showError("Please enter a city name")
            return
        }

        // Clear previous errors and show loading
        hideError()
        showLoading(true)

        // Use Coroutines for network call
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = weatherService.getWeather(cityName, "8267882bacb857c78cbec307780a8309")

                // Update UI on main thread
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    displayWeather(response)
                }
            } catch (e: Exception) {
                // Handle errors
                withContext(Dispatchers.Main) {
                    showLoading(false)
                    showError("Failed to get weather: ${e.message}")
                }
            }
        }
    }

    private fun displayWeather(weather: WeatherResponse) {
        cityTextView.text = "${weather.name}"
        tempTextView.text = "${weather.main.temp}°C"

        // You can add more fields here like:
        // humidityTextView.text = "Humidity: ${weather.main.humidity}%"
        // weatherDescTextView.text = "Description: ${weather.weather[0].description}"
    }

    private fun showLoading(show: Boolean) {
        progressBar.visibility = if (show) ProgressBar.VISIBLE else ProgressBar.GONE
        fetchButton.isEnabled = !show
    }

    private fun showError(message: String) {
        errorTextView.text = message
        errorTextView.visibility = TextView.VISIBLE
    }

    private fun hideError() {
        errorTextView.visibility = TextView.GONE
    }
}