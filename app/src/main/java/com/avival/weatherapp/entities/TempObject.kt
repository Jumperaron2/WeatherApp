package com.avival.weatherapp.entities

import com.google.gson.annotations.SerializedName

data class TempObject(@SerializedName("temp") var temp:Double,
                      @SerializedName("feels_like") var feels:Double,
                      @SerializedName("temp_min") var tempMin:Double,
                      @SerializedName("temp_max") var tempMax:Double,
                      @SerializedName("pressure") var pressure:Int,
                      @SerializedName("humidity") var humidity:Int)