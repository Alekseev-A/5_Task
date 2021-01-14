package com.example.weatherrx.ui.cities

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import com.example.weatherrx.App
import com.example.weatherrx.R
import com.example.weatherrx.databinding.FragmentCitiesBinding
import com.example.weatherrx.ui.cities.adapter.CitiesAdapter
import com.example.weatherrx.ui.core.Fragment
import javax.inject.Inject


class CitiesListFragment : Fragment(R.layout.fragment_cities) {

    private var _binding: FragmentCitiesBinding? = null
    private val binding get() = _binding!!


//    lateinit var viewModel: CitiesListViewModel
//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewAdapter = CitiesAdapter(
        {
//            viewModel.onCityClick(it)
        },
        {
//            viewModel.onCityLongClick(it)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        App.dagger?.inject(this)
        _binding = FragmentCitiesBinding.bind(view)
//        viewModel = ViewModelProvider(this, viewModelFactory)[CitiesListViewModel::class.java]

        initSwipeToRefresh()
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    private fun initSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
//            Toast.makeText(requireActivity(), viewModel.toString(), Toast.LENGTH_LONG).show()
        }
        binding.swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun RecyclerView.setup() {
        layoutManager = LinearLayoutManager(activity)

        adapter = this@CitiesListFragment.viewAdapter
        viewAdapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY

        addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            ).also {
                val drawable = GradientDrawable(
                    GradientDrawable.Orientation.BOTTOM_TOP,
                    intArrayOf(solidColor, solidColor)
                )
                drawable.setSize(0, 20)
                it.setDrawable(drawable)
            }
        )
    }

}