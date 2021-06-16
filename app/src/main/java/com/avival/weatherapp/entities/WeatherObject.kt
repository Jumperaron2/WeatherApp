package com.avival.weatherapp.entities

import com.google.gson.annotations.SerializedName

data class WeatherObject(@SerializedName("id") var id:Int,
                         @SerializedName("main") var main:String,
                         @SerializedName("description") var description:String,
                         @SerializedName("icon") var icon:String)