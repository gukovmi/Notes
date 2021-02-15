package com.example.notes.presentation.viewmodel

import com.example.notes.ui.Screens
import com.github.terrakok.cicerone.Router
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

	private val router: Router = mock()

	private val viewModel = MainViewModel(router)

	@Test
	fun `navigateToNotesList EXPECT router navigate to notes list screen`() {
		viewModel.navigateToNotesList()

		verify(router).replaceScreen(Screens.NotesList)
	}
}