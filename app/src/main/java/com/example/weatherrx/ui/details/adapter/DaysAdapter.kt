package com.example.weatherrx.ui.details.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherrx.R
import com.example.weatherrx.data.entities.Day
import com.example.weatherrx.databinding.CityHolderInCardViewBinding
import com.example.weatherrx.ui.cities.CityViewItem
import com.example.weatherrx.ui.cities.adapter.CitiesAdapter
import com.example.weatherrx.utils.toCardinalPoints
import com.example.weatherrx.utils.toTemperature

class DaysAdapter(): ListAdapter<Day, DaysAdapter.DayItemHolder>(DayDiffCallback()) {

    inner class DayItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: CityHolderInCardViewBinding by lazy(LazyThreadSafetyMode.NONE) {
            CityHolderInCardViewBinding.bind(itemView)
        }

        lateinit var cityViewItem: CityViewItem

        fun bind() {
            renderCityName()
            renderPressure()
            renderTemperature()
            renderWind()
            renderIcon()
        }

        fun renderCityName() {
            binding.cityHolder.cityNameTextView.text = cityViewItem.city.name
        }

        fun renderPressure() {
            binding.cityHolder.pressureTextView.text = cityViewItem.forecast.pressure.toString()
        }

        fun renderTemperature() {
            binding.cityHolder.tempTextView.text = cityViewItem.forecast.temp.toTemperature()
        }

        fun renderWind() {
            binding.cityHolder.windTextView.text =
                binding.root.context.getString(
                    cityViewItem.forecast.windDeg.toCardinalPoints(),
                    cityViewItem.forecast.windSpeed.toString(),
                    binding.root.context.getString(R.string.speed)
                )
        }

        fun renderIcon() {
            Glide
                .with(binding.root)
                .load("https://openweathermap.org/img/wn/${cityViewItem.forecast.icon}@2x.png")
                .into(binding.cityHolder.iconImageView)
        }
    }
    class DayDiffCallback : DiffUtil.ItemCallback<Day>() {

        override fun areItemsTheSame(
            oldItem: Day,
            newItem: Day
        ): Boolean =
            oldItem.date == newItem.date

        override fun areContentsTheSame(
            oldItem: Day,
            newItem: Day
        ): Boolean = oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayItemHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DayItemHolder, position: Int) {
        TODO("Not yet implemented")
    }
}
