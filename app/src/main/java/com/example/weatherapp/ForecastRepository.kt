package com.example.weatherapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.api.CurrentWeather
import com.example.weatherapp.api.WeeklyForecast
import com.example.weatherapp.api.createOpenWeatherMapService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.random.Random

class ForecastRepository {

    private val _currentWeather = MutableLiveData<CurrentWeather>()
    val currentWeather:LiveData<CurrentWeather> = _currentWeather
    private val _weeklyForecast = MutableLiveData<WeeklyForecast>()
    val weeklyForecast: LiveData<WeeklyForecast> = _weeklyForecast


    fun loadWeeklyForecast(city: String){
        val call = createOpenWeatherMapService().currentWeather(city, "metric", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<CurrentWeather> {
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    // 7 day forecast
                    val call = createOpenWeatherMapService().sevenDayForecastе(
                        lat =weatherResponse.coord.lat,
                        lon =  weatherResponse.coord.lon,
                        exclude = "current,minutely,hourly",
                        units = "metric",
                        apiKey = BuildConfig.OPEN_WEATHER_MAP_API_KEY
                    )
                    call.enqueue(object : Callback<WeeklyForecast> {
                        override fun onResponse(
                            call: Call<WeeklyForecast>,
                            response: Response<WeeklyForecast>
                        ) {
                            val weeklyForecastResponse = response.body()
                            if (weeklyForecastResponse != null) {
                                _weeklyForecast.value = weeklyForecastResponse
                            }
                        }

                        override fun onFailure(call: Call<WeeklyForecast>, t: Throwable) {
                            Log.e("ForecastRepository", "Error Loading weekly forecast", t)
                        }

                    })
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e("ForecastRepository", "Error Loading location for weekly forecast", t)
            }

        })
    }

    fun loadCurrentForecast(city: String){

        val call = createOpenWeatherMapService().currentWeather(city, "metric", BuildConfig.OPEN_WEATHER_MAP_API_KEY)
        call.enqueue(object : Callback<CurrentWeather> {
            override fun onResponse(
                call: Call<CurrentWeather>,
                response: Response<CurrentWeather>
            ) {
                val weatherResponse = response.body()
                if (weatherResponse != null) {
                    _currentWeather.value = weatherResponse
                }
            }

            override fun onFailure(call: Call<CurrentWeather>, t: Throwable) {
                Log.e("ForecastRepository", "Error Loading Current Weather", t)
            }

        })
    }



}