package com.example.weatherrx.ui.core

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

open class Fragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId),
    Screen by ScreenImpl() {

    override fun onResume() {
        isActive = true
        super.onResume()
    }

    override fun onStop() {
        isActive = false
        super.onStop()
    }
}

interface Screen {
    fun observeVM(): Disposable = Disposable.disposed()
    var isActive: Boolean
    fun <T> Observable<T>.observe(accept: (T) -> Unit): Disposable
}

class ScreenImpl : Screen {

    private var disposable = Disposable.disposed()

    override var isActive: Boolean = false
        set(isActive) {
            if (field == isActive) return
            field = isActive

            if (isActive) {
                disposable = observeVM()
            } else {
                disposable.dispose()
            }
        }

    override fun <T> Observable<T>.observe(accept: (T) -> Unit): Disposable =
        observeOn(AndroidSchedulers.mainThread())
            .subscribe(accept)
}