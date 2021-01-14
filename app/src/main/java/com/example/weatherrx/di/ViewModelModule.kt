package com.example.weatherrx.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherrx.ui.cities.CitiesListViewModel
import com.example.weatherrx.ui.core.ViewModelFactory
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass


@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory
    ): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CitiesListViewModel::class)
    internal abstract fun citiesListViewModel(viewModel: CitiesListViewModel): ViewModel

    @MustBeDocumented
    @Target(AnnotationTarget.FUNCTION,
        AnnotationTarget.PROPERTY_GETTER,
        AnnotationTarget.PROPERTY_SETTER)
    @Retention(AnnotationRetention.RUNTIME)
    @MapKey
    annotation class ViewModelKey(val value: KClass<out ViewModel>)
}

