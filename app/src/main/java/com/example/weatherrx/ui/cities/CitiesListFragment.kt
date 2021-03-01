package com.example.weatherrx.ui.cities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: CitiesListViewModel
    private val adapter = CitiesAdapter {
        viewModel.onCityClick(it)
    }
    private var _binding: FragmentCitiesBinding? = null
    private val binding: FragmentCitiesBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.dagger?.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[CitiesListViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCitiesBinding.bind(view).setup()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun observeVM(): Disposable = CompositeDisposable(
        viewModel.citiesBehaviorSubject.observe(
            adapter::submitList
        ),
        viewModel.isUpdatingBehaviorSubject.observe {
            binding.swipeRefreshLayout.isRefreshing = it
        },
        viewModel.forShowingPublishSubject.observe {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_LONG).show()
        }
    )

    private fun FragmentCitiesBinding.setup(): FragmentCitiesBinding {
        fab.setOnClickListener { viewModel.onFabClick() }

        swipeRefreshLayout.setup()
        recyclerView.setup()
        return this
    }

    private fun RecyclerView.setup() {
        layoutManager = LinearLayoutManager(activity)
        overScrollMode = View.OVER_SCROLL_NEVER

        adapter = this@CitiesListFragment.adapter
//        adapter?.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY

        val callback: ItemTouchHelper.Callback = ItemTouchHelperCallback(
            onItemMove = ::swapCitiesIndexesByPosition,
            onItemDismiss = ::deleteCityByPosition,
            onDrag = { binding.swipeRefreshLayout.isEnabled = !it }
        )
        val touchHelper = ItemTouchHelper(callback)
        touchHelper.attachToRecyclerView(this)

    }

    private fun deleteCityByPosition(position: Int) {
        viewModel.selectCityForDelete(adapter.getItem(position))
    }

    private fun swapCitiesIndexesByPosition(fromPosition: Int, newPosition: Int) {
        viewModel.changeCityPosition(
            adapter.getItem(fromPosition),
            adapter.getItem(newPosition).city.position
        )
    }

    private fun SwipeRefreshLayout.setup() {
        setOnRefreshListener {
            viewModel.update()
        }
        setColorSchemeResources(
            R.color.teal_200
        )
    }
}
