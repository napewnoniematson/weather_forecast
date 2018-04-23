package com.matsondream.weatherforecast.json

import android.util.Log
import com.matsondream.weatherforecast.model.City
import com.matsondream.weatherforecast.model.Weather
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Matson on 20.04.2018.
 */
class JsonConverter {

    @Throws(JSONException::class)
    fun getWeather(json : String) : Weather {
        val weather = Weather()
        val obj = JSONObject(json)

        weather.iconDesc = obj.getJSONArray("weather").getJSONObject(0).getString("main")
        weather.desc = obj.getJSONArray("weather").getJSONObject(0).getString("description")
        weather.temp = obj.getJSONObject("main").getString("temp")
        weather.pressure = obj.getJSONObject("main").getString("pressure")
        weather.humidity = obj.getJSONObject("main").getString("humidity")
        weather.windSpd = obj.getJSONObject("wind").getString("speed")
        //weather.country = obj.getJSONObject("sys").getString("country")
        //weather.city = obj.getString("name")

        return weather
    }

    @Throws(JSONException::class)
    fun getForecast(json: String) : List<Weather> {
        val forecast = mutableListOf<Weather>()
        val list = JSONObject(json).getJSONArray("list")


        for (i in 0 until list.length()) {
            val weather = Weather()
            val obj = list.getJSONObject(i)

            weather.iconDesc = obj.getJSONArray("weather").getJSONObject(0).getString("main")
            weather.desc = obj.getJSONArray("weather").getJSONObject(0).getString("description")
            weather.temp = obj.getJSONObject("main").getString("temp")
            weather.pressure = obj.getJSONObject("main").getString("pressure")
            weather.humidity = obj.getJSONObject("main").getString("humidity")
            weather.windSpd = obj.getJSONObject("wind").getString("speed")
            val datatime = obj.getString("dt_txt").split(" ")
            weather.date = datatime[0]
            weather.time = datatime[1]
            forecast.add(weather)
        }
        return forecast.toList()
    }

    @Throws(JSONException::class)
    fun getCity(json: String) : City {
        val city = City()
        val obj = JSONObject(json).getJSONObject("city")

        city.name = obj.getString("name")
        city.country = obj.getString("country")

        return city
    }

}