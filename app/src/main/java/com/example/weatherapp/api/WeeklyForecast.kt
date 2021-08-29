package com.example.weatherapp.api

import com.squareup.moshi.Json


data class WeatherDescription(
    val main: String,
    val description: String,
    val icon: String
)

data class Temp(
    val min:Float,
    val max:Float,

)

data class DailyForecast(
    val pressure: String,
    val humidity: String,
    val wind_speed: String,
    val wind_deg: String,
    val clouds: String,


    @field:Json(name = "dt")val date:Long,
    val temp: Temp,
    val weather: List<WeatherDescription>,
)

data class WeeklyForecast(
    val daily: List<DailyForecast>
)