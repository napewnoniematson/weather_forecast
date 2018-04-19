package com.matsondream.weatherforecast.json

/**
 * Created by Matson on 19.04.2018.
 */
interface HTTPHandler {
    fun makeServiceCall(reqUrl: String): String?
}