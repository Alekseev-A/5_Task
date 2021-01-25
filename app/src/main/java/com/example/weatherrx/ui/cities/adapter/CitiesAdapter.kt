package com.example.weatherrx.ui.cities.adapter

import android.view.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherrx.R
import com.example.weatherrx.databinding.CityHolderInCardViewBinding
import com.example.weatherrx.ui.cities.CityViewItem
import com.example.weatherrx.utils.toCardinalPoints
import com.example.weatherrx.utils.toTemperature
import kotlin.math.roundToInt


class CitiesAdapter(
    private val onClick: (CityViewItem) -> Unit,
) : ListAdapter<CityViewItem, CitiesAdapter.CityViewItemHolder>(CityViewItemDiffCallback()) {

    inner class CityViewItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding: CityHolderInCardViewBinding by lazy(LazyThreadSafetyMode.NONE) {
            CityHolderInCardViewBinding.bind(itemView)
        }

        lateinit var cityViewItem: CityViewItem

        init {
            binding.root.setOnClickListener { onClick(cityViewItem) }
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

    class CityViewItemDiffCallback : DiffUtil.ItemCallback<CityViewItem>() {

        override fun areItemsTheSame(
            oldItem: CityViewItem,
            newItem: CityViewItem
        ): Boolean =
            oldItem.city.id == newItem.city.id

        override fun areContentsTheSame(
            oldItem: CityViewItem,
            newItem: CityViewItem
        ): Boolean = oldItem == newItem

        override fun getChangePayload(
            oldItem: CityViewItem,
            newItem: CityViewItem
        ) = mutableListOf<CityViewItemHolder.() -> Unit>().apply {
            if (oldItem.forecast.name != newItem.forecast.name) add { renderCityName() }
            if (oldItem.forecast.pressure != newItem.forecast.pressure) add { renderPressure() }
            if (oldItem.forecast.temp != newItem.forecast.temp) add { renderTemperature() }
            if (oldItem.forecast.windDeg != newItem.forecast.windDeg ||
                oldItem.forecast.windSpeed != newItem.forecast.windSpeed
            ) add { renderWind() }
            if (oldItem.forecast.icon != newItem.forecast.icon) add { renderIcon() }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CityViewItemHolder(
        LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.city_holder_in_card_view,
                parent,
                false
            )
    )

    override fun onBindViewHolder(itemHolder: CityViewItemHolder, position: Int) {
        itemHolder.cityViewItem = getItem(position)
        itemHolder.bind()
    }

    override fun onBindViewHolder(
        itemHolder: CityViewItemHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        itemHolder.cityViewItem = getItem(position)

        if (payloads.isEmpty()) {
            itemHolder.bind()
        }

        @Suppress("UNCHECKED_CAST")
        for (changes in payloads as List<List<CityViewItemHolder.() -> Unit>>) {
            for (change in changes) {
                change(itemHolder)
            }
        }
    }

    public override fun getItem(position: Int): CityViewItem = super.getItem(position)

}