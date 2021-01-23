package com.example.notes.ui.utils

import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class Binder<T>(
	owner: LifecycleOwner,
	private val liveData: MutableLiveData<T>,
	private val setter: (T) -> Unit
) {

	private var settingValue = false

	init {
		liveData.observe(owner, Observer { value ->
			if (!settingValue) {
				settingValue = true
				setter(value)
				settingValue = false
			}
		})
	}

	fun setValue(value: T) {
		if (!settingValue) {
			settingValue = true
			liveData.value = value
			settingValue = false
		}
	}
}

fun <T> MutableLiveData<T>.bind(owner: LifecycleOwner, setter: (T) -> Unit): Binder<T> =
	Binder(owner, this, setter)

fun MutableLiveData<String>.bind(owner: LifecycleOwner, editText: EditText) {
	val binder = bind(owner, editText::setText)
	editText.addTextChangedListener { binder.setValue(it.toString()) }
}
