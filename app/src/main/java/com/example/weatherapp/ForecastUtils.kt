package com.example.weatherapp

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun formatTempForDisplay(temp: Float, setting: TempDisplaySetting): String{
    return when(setting){
        TempDisplaySetting.Celsius ->{

            String.format("%.2f°C", temp)
        }

        TempDisplaySetting.Fahrenheit ->{
            val tempValue = temp + 32f * (9f/5f)
            String.format("%.2f°F", tempValue)
        }

    }
}


fun showTempDisplaySettingDialog(context: Context, tempDisplaySettingManager: TempDisplaySettingManager){
    val dialogBuilder = AlertDialog.Builder(context)
        .setTitle("Choose Display Units")
        .setMessage("Choose which temperature unit to use for temperature display")
        .setPositiveButton("F°"){_,_ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Fahrenheit)
        }
        .setNeutralButton("C°"
        ) { _, _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celsius)
        }
        .setOnDismissListener{
            Toast.makeText(context, "setting will take affect on app restart"
                , Toast.LENGTH_SHORT).show()
        }

    dialogBuilder.show()

}
