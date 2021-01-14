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
import com.example.weatherrx.databinding.CityHolderForRvBinding


class CitiesAdapter(
    private val onClick: (City) -> Unit,
    private val onLongClick: (City) -> Unit
) : ListAdapter<City, CitiesAdapter.NotesViewHolder>(NotesDiffCallback()) {

    inner class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: CityHolderForRvBinding by lazy(LazyThreadSafetyMode.NONE) {
            CityHolderForRvBinding.bind(itemView)
        }

        lateinit var city: City

        init {
            binding.root.setOnClickListener { onClick(city) }
            binding.root.setOnLongClickListener {
                onLongClick(city)
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
            binding.cityHolder.cityNameTextView.text = city.name
        }

        fun renderPressure() {
            binding.cityHolder.pressureTextView.text = city.main.pressure.toString()
        }

        fun renderTemperature() {
            binding.cityHolder.tempTextView.text = city.main.temp.toString()
        }

        fun renderWind() {
            binding.cityHolder.windTextView.text =
                binding.root.context.getString(
                    windDirection(city.wind.deg),
                    city.wind.speed.toString(),
                    R.string.speed
                )
        }

        fun renderIcon() {
            Glide
                .with(binding.root)
                .load(city.weather[0].icon)
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

    class NotesDiffCallback : DiffUtil.ItemCallback<City>() {

        override fun areItemsTheSame(
            oldItem: City,
            newItem: City
        ): Boolean = oldItem.cityId == oldItem.cityId

        override fun areContentsTheSame(
            oldItem: City,
            newItem: City
        ): Boolean = oldItem == newItem

        override fun getChangePayload(
            oldItem: City,
            newItem: City
        ) = mutableListOf<NotesViewHolder.() -> Unit>().apply {
            if (oldItem.name != newItem.name) add { renderCityName() }
            if (oldItem.main.pressure != newItem.main.pressure) add { renderPressure() }
            if (oldItem.main.temp != newItem.main.temp) add { renderTemperature() }
            if (oldItem.wind != newItem.wind) add { renderWind() }
            if (oldItem.weather[0].icon != newItem.weather[0].icon) add { renderIcon() }
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
        holder.city = getItem(position)
        holder.bind()
    }

    override fun onBindViewHolder(
        holder: NotesViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.city = getItem(position)

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