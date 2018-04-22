package com.matsondream.weatherforecast.model

/**
 * Created by Matson on 19.04.2018.
 */
data class Weather (var iconDesc : String = "",
                    var desc : String = "",
                    var temp : String = "",
                    var pressure : String = "",
                    var humidity : String = "",
                    var windSpd : String = "",
                    var dt_txt : String = "")

