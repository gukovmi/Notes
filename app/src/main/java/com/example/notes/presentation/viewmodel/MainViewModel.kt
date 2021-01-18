package com.example.notes.presentation.viewmodel

import com.example.notes.presentation.base.BaseViewModel
import com.example.notes.ui.Screens
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class MainViewModel @Inject constructor(
	private val router: Router
) : BaseViewModel() {

	fun navigateToNotesList() {
		router.replaceScreen(Screens.notesList())
	}
}