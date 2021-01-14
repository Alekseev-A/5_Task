package com.example.weatherrx.ui.details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.weatherrx.R
import com.example.weatherrx.databinding.FragmentSecondBinding
import com.example.weatherrx.ui.core.Fragment


class DetailsFragment : Fragment(R.layout.fragment_second) {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSecondBinding.bind(view)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}