package com.example.weatherrx.ui.details

import android.os.Bundle
import android.view.View
import com.example.weatherrx.R
import com.example.weatherrx.databinding.FragmentDetailsBinding
import com.example.weatherrx.ui.core.Fragment


class DetailsFragment : Fragment(R.layout.fragment_details) {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDetailsBinding.bind(view)

        binding.buttonSecond.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}