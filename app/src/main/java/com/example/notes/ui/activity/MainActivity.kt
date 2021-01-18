package com.example.notes.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notes.App
import com.example.notes.R
import com.example.notes.presentation.viewmodel.MainViewModel
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	lateinit var viewModel: MainViewModel

	@Inject
	lateinit var navigatorHolder: NavigatorHolder
	private val navigator = AppNavigator(this, R.id.container)

	override fun onCreate(savedInstanceState: Bundle?) {
		App.component.injectMainActivity(this)
		viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		if (savedInstanceState == null) {
			viewModel.navigateToNotesList()
		}
	}

	override fun onResumeFragments() {
		super.onResumeFragments()
		navigatorHolder.setNavigator(navigator)
	}

	override fun onPause() {
		navigatorHolder.removeNavigator()
		super.onPause()
	}
}