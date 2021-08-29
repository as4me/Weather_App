package com.example.weatherapp.api

import com.squareup.moshi.Json

data class Forecast(val temp: Float,val pressure:Int,val humidity:Int) {
}

data class Coordinates(val lat: Float, val lon: Float)

data class Clouds(val all: Int)

data class Wind(val speed:Float,val deg:Int)

data class Weather(val main:String,val description: String, val icon: String)

data class CurrentWeather(
    val name: String,
    val coord: Coordinates,
    @field:Json(name = "main") val forecast: Forecast,
    val clouds: Clouds,
    val wind: Wind,
    val weather: List<Weather>,
)