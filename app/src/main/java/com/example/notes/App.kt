package com.example.notes

import android.app.Application
import com.example.notes.di.component.AppComponent
import com.example.notes.di.component.DaggerAppComponent
import com.example.notes.di.module.ContextModule

class App : Application() {

	companion object {

		lateinit var component: AppComponent
	}

	override fun onCreate() {
		super.onCreate()
		component = DaggerAppComponent
			.builder()
			.contextModule(ContextModule(this.applicationContext))
			.build()
	}
}