package com.example.notes.presentation.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {

	private val compositeDisposable = CompositeDisposable()

	override fun onCleared() {
		compositeDisposable.dispose()
	}

	fun Disposable.addToComposite() {
		compositeDisposable.add(this)
	}
}