package com.matsondream.weatherforecast

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.matsondream.weatherforecast.constants.Constants
import com.matsondream.weatherforecast.constants.WeatherConditionCodes
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSearchButtonOnClick()
    }

    private fun initSearchButtonOnClick() {
        searchImgBtn.setOnClickListener {
            val intent = getIntentWithData()
            startWeatherActivity(intent)
        }
    }

    private fun startWeatherActivity(intent: Intent) {
        if (isNetworkAvailable())
            startActivity(intent)
        else
            showNetworkUnavailableMessage("No internet access")
    }

    private fun getIntentWithData() : Intent{
        val city = cityET.text.toString()
        val country = countryET.text.toString()
        return Intent(this, WeatherActivity::class.java).apply {
            putExtra(Constants.CITY, city)
            putExtra(Constants.COUNTRY, country)
        }

    }

    private fun isNetworkAvailable() : Boolean {
        var connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    private fun showNetworkUnavailableMessage(msg : String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
