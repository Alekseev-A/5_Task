package com.example.weatherrx.ui

import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.weatherrx.R
import com.example.weatherrx.ui.cities.CitiesListViewModel
import com.example.weatherrx.ui.cities.CityViewItem
import kotlin.reflect.KClass

object Router {
    private lateinit var navController: NavController

    private val fragmentsWithActions = mapOf<KClass<out ViewModel>, FragmentNavigation>(
        Pair(CitiesListViewModel::class, FragmentNavigation.ForCitiesList)
    )

    fun initNavController(mainActivity: MainActivity) {
        navController = mainActivity.findNavController(R.id.nav_host_fragment_container)
    }

    fun getNavigation(viewModel: ViewModel): FragmentNavigation =
        fragmentsWithActions[viewModel::class] ?: error("This viewModel doesn't contains in Router")

    sealed class FragmentNavigation {
        object ForCitiesList : FragmentNavigation() {
            fun toFindFragment() =
                navController.navigate(R.id.action_CitiesListFragment_to_findFragment)

            fun toDetailsFragment(cityViewItem: CityViewItem) =
                navController.navigate(
                    R.id.action_CitiesListFragment_to_DetailsFragment,
                    bundleOf(Pair("city", cityViewItem))
                )
        }
    }
}