package com.example.weatherrx.ui.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

abstract class Fragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {
    private var disposable = Disposable.disposed()

    var isActive: Boolean = false
        set(isActive) {
            if (field == isActive) return
            field = isActive

            if (isActive) disposable = observeVM()
            else disposable.dispose()
        }

    abstract fun observeVM(): Disposable

    override fun onResume() {
        isActive = true
        super.onResume()
    }

    override fun onStop() {
        isActive = false
        super.onStop()
    }

    fun <T> Observable<T>.observe(
        accept: (T) -> Unit,
    ): Disposable =
        observeOn(AndroidSchedulers.mainThread())
            .subscribe(accept)
}