package com.example.weatherrx.ui.details

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherrx.App
import com.example.weatherrx.R
import com.example.weatherrx.data.entities.City
import com.example.weatherrx.databinding.FragmentDetailsBinding
import com.example.weatherrx.ui.cities.CityViewItem
import com.example.weatherrx.ui.cities.adapter.ItemTouchHelperCallback
import com.example.weatherrx.ui.core.Fragment
import com.example.weatherrx.ui.details.adapter.DaysAdapter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject


class DetailsFragment : Fragment(R.layout.fragment_details) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailsViewModel
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var cityViewItem: CityViewItem
    private val adapter = DaysAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.dagger?.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailsViewModel::class.java]
        cityViewItem = requireArguments().getParcelable("city")!!
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailsBinding.bind(view).setup()
    }

    override fun observeVM(): Disposable = CompositeDisposable(
        viewModel.getDays(cityViewItem.city).observe {
            Toast.makeText(requireActivity(), it.toString(), Toast.LENGTH_SHORT).show()
        }
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FragmentDetailsBinding.setup(): FragmentDetailsBinding {
        recyclerView.setup()
        return this
    }

    private fun RecyclerView.setup() {
        layoutManager = LinearLayoutManager(activity)

        adapter = this@DetailsFragment.adapter

        addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}