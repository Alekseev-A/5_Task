package com.example.weatherrx.ui.find

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.weatherrx.App
import com.example.weatherrx.R
import com.example.weatherrx.data.entities.CityForecast
import com.example.weatherrx.databinding.FragmentFindBinding
import com.example.weatherrx.ui.core.Fragment
import com.example.weatherrx.utils.toCardinalPoints
import com.example.weatherrx.utils.toTemperature
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject


class FindFragment : Fragment(R.layout.fragment_find) {

    lateinit var viewModel: FindViewModel

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
        observeVM()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun observeVM(): Disposable {
        return CompositeDisposable(
            viewModel.foundCityBehaviourSubject.observe { forecast ->
                setupUI(forecast)
            },
            viewModel.onBackPressedPublishSubject.observe {
                if (it) requireActivity().onBackPressed()
            },
            viewModel.forShowingPublishSubject.observe {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun setupUI(forecast: CityForecast) {
        binding.cityHolderInCardView.root.setOnClickListener {
            viewModel.clickOnCity(forecast.cityId)
        }
        binding.cityHolderInCardView.root.visibility = View.VISIBLE
        binding.cityHolderInCardView.cityHolder.cityNameTextView.text = forecast.name
        binding.cityHolderInCardView.cityHolder.pressureTextView.text =
            forecast.pressure.toString()
        binding.cityHolderInCardView.cityHolder.tempTextView.text =
            forecast.temp.toTemperature()
        binding.cityHolderInCardView.cityHolder.windTextView.text =
            binding.root.context.getString(
                forecast.windDeg.toCardinalPoints(),
                forecast.windSpeed.toString(),
                binding.root.context.getString(R.string.speed)
            )
        Glide
            .with(requireActivity())
            .load("https://openweathermap.org/img/wn/${forecast.icon}@2x.png")
            .into(binding.cityHolderInCardView.cityHolder.iconImageView)
    }

    private fun FragmentFindBinding.setup(): FragmentFindBinding {
        editTextTextPersonName.post {
            editTextTextPersonName.requestFocus()
            editTextTextPersonName.onKeyUp(KeyEvent.KEYCODE_DPAD_CENTER,
                KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_CENTER))
        }

//        editTextTextPersonName.isFocusableInTouchMode = true
//        editTextTextPersonName.touch
        editTextTextPersonName.doOnTextChanged { name, _, _, _ ->
            viewModel.setNameForSearch(name.toString())
        }
        return this
    }
}