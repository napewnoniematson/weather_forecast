package com.matsondream.weatherforecast.json

/**
 * Created by Matson on 19.04.2018.
 */
interface UrlProvider {
    fun getUrl(city : String, country : String) : String
}