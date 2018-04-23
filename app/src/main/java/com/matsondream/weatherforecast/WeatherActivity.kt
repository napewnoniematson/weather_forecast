package com.matsondream.weatherforecast

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.matsondream.exchangerates.HTTPHandlerImpl
import com.matsondream.weatherforecast.R.mipmap.*
import com.matsondream.weatherforecast.adapter.ForecastRecyclerAdapter
import com.matsondream.weatherforecast.constants.Constants
import com.matsondream.weatherforecast.constants.WeatherConditionCodes
import com.matsondream.weatherforecast.json.JsonConverter
import com.matsondream.weatherforecast.json.UrlProviderImpl
import com.matsondream.weatherforecast.model.City
import com.matsondream.weatherforecast.model.Weather
import kotlinx.android.synthetic.main.activity_weather.*
import java.io.IOException
import java.net.MalformedURLException

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        incomingCall()
    }

    private fun incomingCall() {
        val city = intent.getStringExtra(Constants.CITY)
        val country = intent.getStringExtra(Constants.COUNTRY)
        val url = UrlProviderImpl().getForecastUrl(city, country)
        WeatherProvider(this, url).execute()
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

    internal fun findImgId(iconDesc : String, time : String) : Int =
        when (iconDesc) {
            WeatherConditionCodes.CLEAR.value -> if (isDay(time)) clear else clearn
            WeatherConditionCodes.RAIN.value -> rain
            WeatherConditionCodes.CLOUDS.value -> clouds
            WeatherConditionCodes.DRIZZLE.value -> drizzle
            WeatherConditionCodes.SNOW.value -> snow
            WeatherConditionCodes.ATMOSPHERE.value -> atmosphere
            WeatherConditionCodes.THUNDERSTORM.value -> thunderstorm
            WeatherConditionCodes.EXTREME.value -> extreme
            else -> clear
        }

    private fun isDay(weatherTime: String) = getHour(weatherTime) in 6..18

    private fun getHour(weatherTime: String) : Int = weatherTime.substring(0..1).toInt()


    private inner class WeatherProvider(val context: Context, val url : String) :
            AsyncTask<Void, Void, Void>() {

        var json : String? = null
        var forecast : List<Weather>? = null
        var city : City? = null

        override fun doInBackground(vararg p0: Void?): Void? {
            try {
                json = HTTPHandlerImpl().makeServiceCall(url)
                val converter = JsonConverter()
                forecast = converter.getForecast(json!!)
                city = converter.getCity(json!!)
                Log.e("WeatherProvider", json)
            }catch (e: MalformedURLException) {
                Log.e("MalformedUrlException: ", e.message)
            }catch (e: IOException) {
                Log.e("IOException: ", e.message)
                this.cancel(true)
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            killProgressBar()
            displayFirstWeather()
            loadForecastView()
        }

        override fun onCancelled() {
            super.onCancelled()
            (context as WeatherActivity).finish()
            showWrongInputMessage()
        }

        private fun killProgressBar() {
            progressBar.visibility = View.GONE
            (progressBar.parent as ViewGroup).removeView(progressBar)
        }

        private fun displayFirstWeather() {
            displayData(forecast!![0], city!!)
            displayingDataLayout.visibility = View.VISIBLE
        }

        private fun loadForecastView() {
            val adapter = ForecastRecyclerAdapter(context, city!!, forecast!!)
            weatherRecyclerView.adapter = adapter
            weatherRecyclerView.layoutManager = LinearLayoutManager(context)
        }

        private fun showWrongInputMessage() {
            Toast.makeText(context,
                    "Your city name or country code is wrong. Please enter correct data",
                    Toast.LENGTH_LONG).show()
        }
    }
}
