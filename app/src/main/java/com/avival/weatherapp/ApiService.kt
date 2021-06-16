package com.avival.weatherapp

import com.avival.weatherapp.entities.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("weather")
    suspend fun getWeather(@Query("q") city:String,
                           @Query("appid") apiKey:String,
                           @Query("lang") language:String,
                           @Query("units") units:String):Response<WeatherResponse>
}