package com.avival.weatherapp.entities

import com.google.gson.annotations.SerializedName

data class WeatherResponse(@SerializedName("name") var name: String,
                           @SerializedName("weather") val weather: List<WeatherObject>,
                           @SerializedName("main") var temperature: TempObject,
                           @SerializedName("wind") var wind:WindObject)