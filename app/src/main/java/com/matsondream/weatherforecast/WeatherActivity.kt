package com.matsondream.weatherforecast

import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.matsondream.exchangerates.HTTPHandlerImpl
import com.matsondream.weatherforecast.R.mipmap.*
import com.matsondream.weatherforecast.adapter.ForecastRecyclerAdapter
import com.matsondream.weatherforecast.constants.WeatherConditionCodes
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

    internal fun displayData(weather: Weather, city: City) {
        placeTV.text = "${city.name}, ${city.country}"
        tempTV.text = "${weather.temp} °C"
        pressureTV.text = "${weather.pressure} hPa"
        humidityTV.text = "${weather.humidity}%"
        windSpdTV.text = "${weather.windSpd} m/s"
        descTV.text = weather.desc
        val imgId = findImgId(weather.iconDesc, weather.time)
        iconImgView.setImageResource(imgId)
    }

    internal fun findImgId(iconDesc : String, time : String) : Int {
        var id : Int = clear
        when (iconDesc) {
            WeatherConditionCodes.CLEAR.value -> if (isDay(time)) id = clear else id = clearn
            WeatherConditionCodes.RAIN.value -> id = rain
            WeatherConditionCodes.CLOUDS.value -> id = clouds
            WeatherConditionCodes.DRIZZLE.value -> id = drizzle
            WeatherConditionCodes.SNOW.value -> id = snow
            WeatherConditionCodes.ATMOSPHERE.value -> id = atmosphere
            WeatherConditionCodes.THUNDERSTORM.value -> id = thunderstorm
            WeatherConditionCodes.EXTREME.value -> id = extreme
        }
        return id
    }

    private fun isDay(weatherTime: String) = getHour(weatherTime) in 6..18

    private fun getHour(weatherTime: String) : Int = weatherTime.substring(0..1).toInt()

    private fun isNetworkAvailable() : Boolean {
        var connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private inner class WeatherProvider(val context: Context, val url : String) :
            AsyncTask<Void, Void, Void>() {

        var json : String? = null
        var forecast : List<Weather>? = null
        var city : City? = null

        override fun doInBackground(vararg p0: Void?): Void? {
            if (isNetworkAvailable()) {
                json = HTTPHandlerImpl().makeServiceCall(url)
                val converter = JsonConverter()
                forecast = converter.getForecast(json!!)
                city = converter.getCity(json!!)
                //Log.e("WeatherProvider", JsonConverter().getWeather(json!!).toString())
                Log.e("WeatherProvider", HTTPHandlerImpl().makeServiceCall(
                        UrlProviderImpl().getForecastUrl("Łódź", "PL")))
            } else {
                Log.e("WeatherProvider", "Network is not available")
                //show toast
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            //displayData(JsonConverter().getWeather(json!!), get)
            progressBar.visibility = View.GONE
            (progressBar.parent as ViewGroup).removeView(progressBar)
            displayData(forecast!![0], city!!)
            displayingDataLayout.visibility = View.VISIBLE
            val adapter = ForecastRecyclerAdapter(context, city!!, forecast!!)
            weatherRecyclerView.adapter = adapter
            weatherRecyclerView.layoutManager = LinearLayoutManager(context)

            Log.e("WeatherProvider", "displayData")
        }
    }
}
