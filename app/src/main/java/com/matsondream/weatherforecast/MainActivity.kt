package com.matsondream.weatherforecast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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

            println("City: ${city}, country: ${country}")
        }
    }
}
