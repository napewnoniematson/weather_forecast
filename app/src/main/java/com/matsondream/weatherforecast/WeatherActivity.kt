package com.matsondream.weatherforecast

import android.content.Context
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.matsondream.exchangerates.HTTPHandlerImpl
import com.matsondream.weatherforecast.constants.Constants
import com.matsondream.weatherforecast.json.UrlProviderImpl

class WeatherActivity : AppCompatActivity() {

    var json : String? = null

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
        val url = UrlProviderImpl().getUrl(city, country)
        WeatherProvider(this, url).execute()
        Log.e("WeatherActivity", "url: $url")
    }

    private inner class WeatherProvider(val context: Context, val url : String) : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg p0: Void?): Void? {
            json = HTTPHandlerImpl().makeServiceCall(url)
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            Log.e("WeatherActivity", "json: $json")
        }

    }



}
