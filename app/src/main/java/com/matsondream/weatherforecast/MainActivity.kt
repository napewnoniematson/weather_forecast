package com.matsondream.weatherforecast

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
            val city = cityET.text.toString()
            val country = countryET.text.toString()
            val intent = Intent(this, WeatherActivity::class.java).apply {
                putExtra(Constants.CITY, city)
                putExtra(Constants.COUNTRY, country)
            }
            startActivity(intent)
        }
    }
}
