package com.example.weatherrx.ui.cities

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatherrx.App
import com.example.weatherrx.R
import com.example.weatherrx.databinding.FragmentCitiesBinding
import com.example.weatherrx.ui.cities.adapter.CitiesAdapter
import com.example.weatherrx.ui.cities.adapter.ItemTouchHelperCallback
import com.example.weatherrx.ui.core.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.*
import javax.inject.Inject


class CitiesListFragment : Fragment(R.layout.fragment_cities) {

    lateinit var viewModel: CitiesListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val adapter = CitiesAdapter(
        onClick = { viewModel.onCityClick(it) }
    )

    private var binding: FragmentCitiesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.dagger?.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[CitiesListViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCitiesBinding.bind(view).setup()
        observeVM()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun observeVM(): Disposable = CompositeDisposable(
        viewModel.citiesBehaviorSubject.observe(
            adapter::submitList
        ),
        viewModel.isUpdatingBehaviorSubject.observe {
            binding?.swipeRefreshLayout?.isRefreshing = it
        },
        viewModel.forShowingPublishSubject.observe {
            activity?.let { activity ->
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        }
    )

    private fun FragmentCitiesBinding.setup(): FragmentCitiesBinding {

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_CitiesListFragment_to_findFragment)
        }

        swipeRefreshLayout.setup()
        recyclerView.setup()
        return this
    }

    private fun RecyclerView.setup() {
        layoutManager = LinearLayoutManager(activity)

        adapter = this@CitiesListFragment.adapter
//        adapter?.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY

        val callback: ItemTouchHelper.Callback = ItemTouchHelperCallback(
            ::swapCitiesIndexesByPosition,
            ::deleteCityByPosition,
            {
                binding!!.swipeRefreshLayout.isEnabled = !it
            }
        )
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(this)

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

    private fun deleteCityByPosition(position: Int) {
        viewModel.selectCityForDelete(adapter.getItem(position))
    }


    private fun swapCitiesIndexesByPosition(fromPosition: Int, newPosition: Int) {
        viewModel.changeCityPosition(adapter.getItem(fromPosition), adapter.getItem(newPosition).city.position)
    }

    private fun SwipeRefreshLayout.setup() {
        setOnRefreshListener {
            viewModel.update()
        }
        setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }
}
