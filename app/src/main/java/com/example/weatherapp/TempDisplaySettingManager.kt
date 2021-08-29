package com.example.weatherapp

import android.content.Context

enum class TempDisplaySetting{
    Fahrenheit, Celsius
}

class TempDisplaySettingManager(context: Context) {

    private val preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun updateSetting(setting: TempDisplaySetting){
        preferences.edit().putString("key_temp_display", setting.name).apply()

    }
    fun getTempDisplaySetting(): TempDisplaySetting {
        val settingsValue = preferences.getString("key_temp_display",
            TempDisplaySetting.Celsius.name) ?: TempDisplaySetting.Celsius.name

        return TempDisplaySetting.valueOf(settingsValue)
    }

}