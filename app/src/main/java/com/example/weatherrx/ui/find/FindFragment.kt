package com.example.weatherrx.ui.find

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherrx.App
import com.example.weatherrx.R
import com.example.weatherrx.data.entities.CityWithForecast
import com.example.weatherrx.databinding.FragmentFindBinding
import com.example.weatherrx.ui.cities.CityViewItem
import com.example.weatherrx.ui.core.Fragment
import com.example.weatherrx.utils.toCardinalPoints
import com.example.weatherrx.utils.toTemperature
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject


class FindFragment : Fragment(R.layout.fragment_find) {

    private lateinit var viewModel: FindViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var _binding: FragmentFindBinding? = null
    private val binding: FragmentFindBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.dagger?.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[FindViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFindBinding.bind(view).setup()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun observeVM(): Disposable {
        return CompositeDisposable(
            viewModel.foundCityBehaviourSubject.observe { cityWithForecast ->
                setupUI(cityWithForecast)
            },
            viewModel.onBackPressedPublishSubject.observe {
                binding.editTextTextPersonName.clearFocus()
                requireActivity().onBackPressed()
            },
            viewModel.forShowingPublishSubject.observe {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun setupUI(cityViewItem: CityWithForecast) {
        binding.cityHolderInCardView.root.setOnClickListener {
            viewModel.clickOnCity()
        }
        binding.cityHolderInCardView.root.visibility = View.VISIBLE
        binding.cityHolderInCardView.cityHolder.cityNameTextView.text = cityViewItem.city.name
        binding.cityHolderInCardView.cityHolder.pressureTextView.text =
            cityViewItem.forecast!!.pressure.toString()
        binding.cityHolderInCardView.cityHolder.tempTextView.text =
            cityViewItem.forecast.temp.toTemperature()
        binding.cityHolderInCardView.cityHolder.windTextView.text =
            binding.root.context.getString(
                cityViewItem.forecast.windDeg.toCardinalPoints(),
                cityViewItem.forecast.windSpeed.toString(),
                binding.root.context.getString(R.string.speed)
            )
        Glide
            .with(requireActivity())
            .load("https://openweathermap.org/img/wn/${cityViewItem.forecast.icon}@2x.png")
            .into(binding.cityHolderInCardView.cityHolder.iconImageView)
    }

    private fun FragmentFindBinding.setup(): FragmentFindBinding {
        editTextTextPersonName.post {
            editTextTextPersonName.requestFocus()
            editTextTextPersonName.onKeyUp(KeyEvent.KEYCODE_DPAD_CENTER,
                KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_CENTER))
        }

        editTextTextPersonName.doOnTextChanged { name, _, _, _ ->
            viewModel.setNameForSearch(name.toString())
        }
        return this
    }
}