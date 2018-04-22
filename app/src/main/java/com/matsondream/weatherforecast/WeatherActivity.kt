package com.matsondream.weatherforecast

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.matsondream.exchangerates.HTTPHandlerImpl
import com.matsondream.weatherforecast.json.JsonConverter
import com.matsondream.weatherforecast.json.UrlProviderImpl
import com.matsondream.weatherforecast.model.City
import com.matsondream.weatherforecast.model.Weather
import kotlinx.android.synthetic.main.activity_weather.*

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        incomingCall()
    }

    private fun incomingCall() {
        //val city = intent.getStringExtra(Constants.CITY)
        //val country = intent.getStringExtra(Constants.COUNTRY)

        val city = "Łódź"
        val country = "PL"
        val url = UrlProviderImpl().getForecastUrl(city, country)
        WeatherProvider(this, url).execute()
        Log.e("WeatherActivity", "url: $url")
    }

    private fun updateView(weather: Weather, city: City){
        placeTV.text = "${city.name}, ${city.country}"
        tempTV.text = "${weather.temp} °C"
        pressureTV.text = "${weather.pressure} hPa"
        humidityTV.text = "${weather.humidity}%"
        windSpdTV.text = "${weather.windSpd} m/s"
        descTV.text = weather.desc
    }

    private inner class WeatherProvider(val context: Context, val url : String) :
            AsyncTask<Void, Void, Void>() {

        var json : String? = null
        var forecast : List<Weather>? = null
        var city : City? = null

        override fun doInBackground(vararg p0: Void?): Void? {
            json = HTTPHandlerImpl().makeServiceCall(url)
            val converter = JsonConverter()
            forecast = converter.getForecast(json!!)
            city = converter.getCity(json!!)
            //Log.e("WeatherProvider", JsonConverter().getWeather(json!!).toString())
            Log.e("WeatherProvider", HTTPHandlerImpl().makeServiceCall(
                    UrlProviderImpl().getForecastUrl("Łódź", "PL")))
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            //updateView(JsonConverter().getWeather(json!!), get)
            forecast!!.forEach {
                weather -> println(weather.dt_txt)
            }
            updateView(forecast!![0], city!!)

            Log.e("WeatherProvider", "updateView")
        }
    }
}
