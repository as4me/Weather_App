package com.example.weatherapp.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.api.load
import com.example.weatherapp.*
import com.example.weatherapp.forecast.CurrentForecastFragmentDirections
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.SimpleDateFormat
import java.util.*

class ForecastDetailsFragment : Fragment() {

    private val DATE_FORMAT = SimpleDateFormat("MM-dd-yyyy")
    private val args: ForecastDetailsFragmentArgs by navArgs()
    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        tempDisplaySettingManager = TempDisplaySettingManager(context = requireContext())


        val view = inflater.inflate(R.layout.fragment_forecast_details, container, false)


        // Temp Text View
        val tempText : TextView = view.findViewById(R.id.tempTextDet)

        val pressureText:TextView = view.findViewById(R.id.pressureDet)

        val humidityText:TextView = view.findViewById(R.id.humidityDet)

        val mainText:TextView = view.findViewById(R.id.mainDet)

        val descriptionText:TextView = view.findViewById(R.id.descriptionDet)

        val speedText:TextView = view.findViewById(R.id.speedDet)

        val cloudsTextView:TextView = view.findViewById(R.id.cloudstextDet)

        val forecastIcon: ImageView = view.findViewById(R.id.forecastIconcurrentDet)
        val dateText: TextView = view.findViewById(R.id.dateDet)

        val temp = args.temp
        tempText.text = formatTempForDisplay(temp, tempDisplaySettingManager.getTempDisplaySetting())
        humidityText.text = "Humidity: " + args.humidity.toString() + "%"
        descriptionText.text = args.description
        mainText.text = args.main
        pressureText.text = "Pressure: " + args.pressure.toString() + "hPa"
        speedText.text = "Wind speed: " + args.speed + "m/s"
        cloudsTextView.text =  "Cloudness: "+ args.cloud + "%"
        dateText.text = DATE_FORMAT.format(Date(args.date * 1000))
        val iconId = args.icon
        forecastIcon.load("http://openweathermap.org/img/wn/${iconId}@4x.png")

        return view
    }


}