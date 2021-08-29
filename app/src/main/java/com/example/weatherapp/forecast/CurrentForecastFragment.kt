package com.example.weatherapp.forecast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import coil.api.load
import com.example.weatherapp.*
import com.example.weatherapp.api.CurrentWeather
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text

class CurrentForecastFragment : Fragment() {


    private val forecastRepository = ForecastRepository()
    private lateinit var locationRepository: LocationRepository
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)

        val locationName:TextView = view.findViewById(R.id.locationName)

        val tempText : TextView = view.findViewById(R.id.tempText)

        val pressureText:TextView = view.findViewById(R.id.pressure)

        val humidityText:TextView = view.findViewById(R.id.humidity)

        val mainText:TextView = view.findViewById(R.id.main)

        val descriptionText:TextView = view.findViewById(R.id.description)

        val speedText:TextView = view.findViewById(R.id.speed)

        val cloudsTextView:TextView = view.findViewById(R.id.cloudstext)

        val forecastIcon:ImageView = view.findViewById(R.id.forecastIconcurrent)

        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)

        locationEntryButton.setOnClickListener {
            showLocationEntry()
        }

        // LocationRepository
        locationRepository = LocationRepository(requireContext())

        // Observer for savedLocation LiveData from LocationRepository
        val savedLocationObserver = Observer<Location> { savedLocation ->
            when(savedLocation){
                is Location.City ->forecastRepository.loadCurrentForecast(savedLocation.city)
            }
        }
        locationRepository.savedLocation.observe(viewLifecycleOwner, savedLocationObserver)

        val currentWeatherObserver = Observer<CurrentWeather> { weather ->

            locationName.text = weather.name
            tempText.text = formatTempForDisplay(weather.forecast.temp, tempDisplaySettingManager.getTempDisplaySetting())
            humidityText.text = "Humidity: " + weather.forecast.humidity.toString() + "%"
            pressureText.text = "Pressure: " + weather.forecast.pressure.toString() + "hPa"
            mainText.text = weather.weather.get(0).main.toString()
            descriptionText.text = weather.weather.get(0).description.toString()
            speedText.text = "Wind speed: " + weather.wind.speed.toString() + "m/s"
            cloudsTextView.text = "Cloudness: "+weather.clouds.all.toString() + "%"
            val iconId = weather.weather[0].icon
            forecastIcon.load("http://openweathermap.org/img/wn/${iconId}@4x.png")
        }

        // observing weekly forecast from repository
        forecastRepository.currentWeather.observe(viewLifecycleOwner, currentWeatherObserver)

        return view

    }

    private fun showLocationEntry() {
        val action = CurrentForecastFragmentDirections.actionCurrentForecastFragmentToLocationEntryFragment()
        findNavController().navigate(action)
    }


}