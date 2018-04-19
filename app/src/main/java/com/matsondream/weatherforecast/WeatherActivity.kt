package com.matsondream.weatherforecast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.matsondream.weatherforecast.constants.Constants

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        incomingCall()
    }

    private fun incomingCall() {
        val city = intent.getStringExtra(Constants.CITY)
        val country = intent.getStringExtra(Constants.COUNTRY)

        println("incoming call: ${city} and ${country}")
    }
}
