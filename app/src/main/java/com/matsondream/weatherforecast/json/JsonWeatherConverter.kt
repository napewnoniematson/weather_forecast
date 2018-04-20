package com.matsondream.weatherforecast.json

import android.util.Log
import com.matsondream.weatherforecast.model.Weather
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Matson on 20.04.2018.
 */
class JsonWeatherConverter {

    @Throws(JSONException::class)
    fun toWeather(json : String) : Weather {
        val weather = Weather()
        val obj = JSONObject(json)

        weather.iconDesc = obj.getJSONArray("weather").getJSONObject(0).getString("main")
        weather.desc = obj.getJSONArray("weather").getJSONObject(0).getString("description")
        weather.temp = obj.getJSONObject("main").getString("temp")
        weather.pressure = obj.getJSONObject("main").getString("pressure")
        weather.humidity = obj.getJSONObject("main").getString("humidity")
        weather.windSpd = obj.getJSONObject("wind").getString("speed")
        weather.clouds = obj.getJSONObject("clouds").getString("all")
        weather.dt = obj.getString("dt")
        weather.country = obj.getJSONObject("sys").getString("country")
        weather.sunrise = obj.getJSONObject("sys").getString("sunrise")
        weather.sunset = obj.getJSONObject("sys").getString("sunset")
        weather.city = obj.getString("name")

        return weather
    }
}