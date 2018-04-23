package com.matsondream.weatherforecast.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.matsondream.weatherforecast.R
import com.matsondream.weatherforecast.model.Weather

/**
 * Created by Matson on 22.04.2018.
 */
class ForecastRecyclerAdapter(private val context : Context, private val forecast : List<Weather>) :
        RecyclerView.Adapter<ForecastRecyclerAdapter.WeatherHolder>() {

    inner class WeatherHolder(view : View) : RecyclerView.ViewHolder(view) {
        var tempTV : TextView
        var dateTV : TextView
        var timeTV : TextView
        var iconImgView : ImageView
        var descTV : TextView

        init {
            tempTV = view.findViewById(R.id.itemTempTV)
            dateTV = view.findViewById(R.id.itemDateTV)
            timeTV = view.findViewById(R.id.itemTimeTV)
            iconImgView = view.findViewById(R.id.itemIconImgView)
            descTV = view.findViewById(R.id.itemDescTV)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastRecyclerAdapter.WeatherHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        //val card : CardView = view.findViewById(R.id.cardView)
        return WeatherHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastRecyclerAdapter.WeatherHolder, position: Int) {
        val weather : Weather= forecast[position]
        holder.tempTV.text = "${weather.temp} °C"
        holder.dateTV.text = weather.date
        holder.timeTV.text = weather.time
        holder.iconImgView.setImageResource(R.mipmap.clear)
        holder.descTV.text = weather.desc
        setAnimation(holder.itemView)
    }

    override fun getItemCount(): Int = forecast.size

    private fun setAnimation(view: View) {
        if (view.animation == null) {
            val animation = AnimationUtils.loadAnimation(view.context, android.R.anim.slide_in_left)
            view.animation = animation
        }
    }

}