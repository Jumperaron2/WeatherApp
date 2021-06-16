package com.avival.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.avival.weatherapp.databinding.ActivityMainBinding
import com.avival.weatherapp.entities.TempObject
import com.avival.weatherapp.entities.WeatherObject
import com.avival.weatherapp.entities.WindObject
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.svWeather.setOnQueryTextListener(this)
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchByCity(city:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getWeather(city,"6eee4a38fa835c11fec271ba5ca255c6","es", "Metric")
            val weather = call.body()
            runOnUiThread {
                if(call.isSuccessful){
                    hideProgress()
                    val cityName = weather?.name ?: ""
                    val cityWeather = weather?.weather?.first()
                    val cityTemp = weather?.temperature
                    val cityWind = weather?.wind
                    showWeather(cityName, cityWeather, cityTemp, cityWind)
                }else{
                    showError()
                }
            }
        }
    }

    private fun showWeather(cityName: String, cityWeather: WeatherObject?, cityTemp: TempObject?, cityWind: WindObject?) {
        val urlIcon = "https://openweathermap.org/img/w/" + cityWeather?.icon + ".png"

        Glide.with(this)
            .load(urlIcon)
            .into(binding.ivIcon)

        binding.tvDescription.text = cityWeather?.description

        val temp = cityTemp?.temp?.toInt().toString() + "°"
        binding.tvTemp.text = temp
        binding.tvCity.text = cityName

        val maxTemp = "${cityTemp?.tempMax?.toInt()}°"
        binding.tvMaxTemp.text = maxTemp

        val minTemp = "${cityTemp?.tempMin?.toInt()}°"
        binding.tvMinTemp.text = minTemp

        var speed:Double = cityWind?.speed ?: 0.0
        speed *= 3.6
        val speedString = speed.toInt().toString() + " kmh"
        binding.tvSpeed.text = speedString

        when(cityWeather?.id){
            in 200..232 -> binding.viewRoot.setBackgroundResource(R.drawable.thunderstorm)
            in 300..321 -> binding.viewRoot.setBackgroundResource(R.drawable.drizzle)
            in 500..531 -> binding.viewRoot.setBackgroundResource(R.drawable.rain)
            in 600..622 -> binding.viewRoot.setBackgroundResource(R.drawable.snow)
            in 701..781 -> binding.viewRoot.setBackgroundResource(R.drawable.atmosphere)
            800 -> binding.viewRoot.setBackgroundResource(R.drawable.clear)
            in 801..804 -> binding.viewRoot.setBackgroundResource(R.drawable.clouds)
            else -> binding.viewRoot.setBackgroundResource(R.drawable.home)
        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showError() {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(this, R.string.api_error, Toast.LENGTH_LONG).show()
    }

    private fun showProgress(){
        binding.viewWelcome.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        binding.progressBar.visibility = View.GONE
        binding.viewCity.visibility = View.VISIBLE
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        hideKeyboard()
        showProgress()
        if(!query.isNullOrEmpty()){
            searchByCity(query.trim().toLowerCase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }
}