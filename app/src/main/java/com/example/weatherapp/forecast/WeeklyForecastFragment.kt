package com.example.weatherapp.forecast

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.*
import com.example.weatherapp.api.DailyForecast
import com.example.weatherapp.api.WeeklyForecast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.log

class WeeklyForecastFragment : Fragment() {


    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_weekly_forecast, container, false)

        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)

        locationEntryButton.setOnClickListener {
            showLocationEntry()
        }

        // forecastList is our RecyclerView
        val forecastList: RecyclerView = view.findViewById(R.id.forecastRecyclerView)

        // setting LinearLayoutManager to RecyclerView.layoutManager
        forecastList.layoutManager = LinearLayoutManager(requireContext())

        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager) { forecast ->

            showForecastDetails(forecast)
        }

        forecastList.adapter = dailyForecastAdapter


        val weeklyForecastObserver = Observer<WeeklyForecast> { weeklyForecast ->

            dailyForecastAdapter.submitList(weeklyForecast.daily)
        }

        // observing weekly forecast from repository
        forecastRepository.weeklyForecast.observe(viewLifecycleOwner, weeklyForecastObserver)

        // loading temp Data
        locationRepository = LocationRepository(requireContext())
        val savedLocationObserver = Observer<Location>{savedLocation ->
            when(savedLocation){
                is Location.City -> forecastRepository.loadWeeklyForecast(savedLocation.city)
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)


        return view

    }

    private fun showLocationEntry() {
        val action = WeeklyForecastFragmentDirections.actionWeeklyForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }

    private fun showForecastDetails(forecast: DailyForecast) {
        val temp = forecast.temp.max
        val description = forecast.weather[0].description
        val main =  forecast.weather[0].main
        val humidity = forecast.humidity
        val pressure = forecast.pressure
        val icon = forecast.weather[0].icon
        val speed = forecast.wind_speed
        val cloud = forecast.clouds
        val date = forecast.date

        Log.d("Test",forecast.toString())
        val action = WeeklyForecastFragmentDirections
            .actionWeeklyForecastFragmentToForecastDetailsFragment(
                temp, description,main,humidity,pressure,icon,speed,cloud,date
            )
        findNavController().navigate(action)
    }




}