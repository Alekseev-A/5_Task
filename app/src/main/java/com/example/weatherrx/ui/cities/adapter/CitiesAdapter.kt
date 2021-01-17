package com.example.weatherrx.ui.cities.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherrx.R
import com.example.weatherrx.data.entities.City
import com.example.weatherrx.data.entities.CityForecast
import com.example.weatherrx.databinding.CityHolderForRvBinding
import com.example.weatherrx.ui.cities.CityViewItem


class CitiesAdapter(
    private val onClick: (CityViewItem) -> Unit,
    private val onLongClick: (CityViewItem) -> Unit
) : ListAdapter<CityViewItem, CitiesAdapter.NotesViewHolder>(NotesDiffCallback()) {

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: CityHolderForRvBinding by lazy(LazyThreadSafetyMode.NONE) {
            CityHolderForRvBinding.bind(itemView)
        }

        lateinit var cityViewItem: CityViewItem

        init {
            binding.root.setOnClickListener { onClick(cityViewItem) }
            binding.root.setOnLongClickListener {
                onLongClick(cityViewItem)
                true
            }
        }

        fun bind() {
            renderCityName()
            renderPressure()
            renderTemperature()
            renderWind()
            renderIcon()
        }

        fun renderCityName() {
            binding.cityHolder.cityNameTextView.text = cityViewItem.forecast.name
        }

        fun renderPressure() {
            binding.cityHolder.pressureTextView.text = cityViewItem.forecast.pressure.toString()
        }

        fun renderTemperature() {
            binding.cityHolder.tempTextView.text = cityViewItem.forecast.temp.toString()
        }

        fun renderWind() {
            binding.cityHolder.windTextView.text =
                binding.root.context.getString(
                    windDirection(cityViewItem.forecast.windDeg),
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

        private fun windDirection(deg: Int): Int =
            when (deg) {
                in 0..22 -> R.string.north
                in 23..67 -> R.string.northeast
                in 68..112 -> R.string.east
                in 113..157 -> R.string.southeast
                in 158..202 -> R.string.south
                in 203..247 -> R.string.southwest
                in 248..292 -> R.string.west
                in 293..337 -> R.string.northwest
                in 338..360 -> R.string.north
                else -> 0
            }
    }

    class NotesDiffCallback : DiffUtil.ItemCallback<CityViewItem>() {

        override fun areItemsTheSame(
            oldItem: CityViewItem,
            newItem: CityViewItem
        ): Boolean = oldItem.city.id == oldItem.city.id

        override fun areContentsTheSame(
            oldItem: CityViewItem,
            newItem: CityViewItem
        ): Boolean = oldItem == newItem

        override fun getChangePayload(
            oldItem: CityViewItem,
            newItem: CityViewItem
        ) = mutableListOf<NotesViewHolder.() -> Unit>().apply {
            if (oldItem.forecast.name != newItem.forecast.name) add { renderCityName() }
            if (oldItem.forecast.pressure != newItem.forecast.pressure) add { renderPressure() }
            if (oldItem.forecast.temp != newItem.forecast.temp) add { renderTemperature() }
            if (oldItem.forecast.windDeg != newItem.forecast.windDeg ||
                oldItem.forecast.windSpeed != newItem.forecast.windSpeed
            ) add { renderWind() }
            if (oldItem.forecast.icon != newItem.forecast.icon) add { renderIcon() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotesViewHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.city_holder_for_rv,
                parent,
                false
            )
    )

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.cityViewItem = getItem(position)
        holder.bind()
    }

    override fun onBindViewHolder(
        holder: NotesViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.cityViewItem = getItem(position)

        if (payloads.isEmpty()) {
            holder.bind()
        }

        @Suppress("UNCHECKED_CAST")
        for (changes in payloads as List<List<NotesViewHolder.() -> Unit>>) {
            for (change in changes) {
                change(holder)
            }
        }
    }
}