package com.matsondream.weatherforecast.json

import com.matsondream.weatherforecast.constants.Constants

/**
 * Created by Matson on 19.04.2018.
 */
class UrlProviderImpl : UrlProvider{
    override fun getWeatherUrl(city: String, country: String): String =
            "http://api.openweathermap.org/data/2.5/weather?q=${city},${country}&appid=${Constants.URL_API}&units=metric"

    override fun getForecastUrl(city: String, country: String): String =
            "http://api.openweathermap.org/data/2.5/forecast?q=${city},${country}&appid=${Constants.URL_API}&units=metric"
}