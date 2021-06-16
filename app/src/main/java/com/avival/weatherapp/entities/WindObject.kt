package com.avival.weatherapp.entities

import com.google.gson.annotations.SerializedName

data class WindObject(@SerializedName("speed") var speed:Double,
                      @SerializedName("deg") var deg:Int,
                      @SerializedName("gust") var gust:Double)