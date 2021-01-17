package com.example.weatherrx.ui.cities

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter.StateRestorationPolicy
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatherrx.App
import com.example.weatherrx.R
import com.example.weatherrx.databinding.FragmentCitiesBinding
import com.example.weatherrx.ui.cities.adapter.CitiesAdapter
import com.example.weatherrx.ui.cities.adapter.ItemTouchHelperCallback
import com.example.weatherrx.ui.core.Fragment
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject


class CitiesListFragment : Fragment(R.layout.fragment_cities) {

    lateinit var viewModel: CitiesListViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val adapter = CitiesAdapter(
        {
//            viewModel.onCityClick(it)
        },
        {
//            viewModel.onCityLongClick(it)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        App.dagger?.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[CitiesListViewModel::class.java]

        FragmentCitiesBinding.bind(view).setup()
        observeVM()
    }

    override fun observeVM(): Disposable {
        return CompositeDisposable(
            viewModel.citiesObservable.observe ({
                Log.d("TAG", "observeVM: $it")
                adapter.submitList(it)
            }, {
                Log.d("TAG", "observeVM: $it")
            })
        )
    }

    private fun FragmentCitiesBinding.setup(): FragmentCitiesBinding {

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        swipeRefreshLayout.setup()
        recyclerView.setup()
        return this
    }

    private fun RecyclerView.setup() {
        layoutManager = LinearLayoutManager(activity)

        adapter = this@CitiesListFragment.adapter
        adapter?.stateRestorationPolicy =
            StateRestorationPolicy.PREVENT_WHEN_EMPTY

        val callback: ItemTouchHelper.Callback =
            ItemTouchHelperCallback(
                { currentPosition: Int, targetPosition: Int -> },
                { currentPosition: Int -> })
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

    private fun SwipeRefreshLayout.setup() {
        setOnRefreshListener {
            Toast.makeText(requireActivity(), viewModel.toString(), Toast.LENGTH_LONG).show()
        }
        setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }

}