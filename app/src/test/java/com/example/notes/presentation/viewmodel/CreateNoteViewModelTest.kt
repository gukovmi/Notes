package com.example.notes.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.notes.domain.entity.Note
import com.example.notes.domain.usecase.CreateNoteUseCase
import com.example.notes.testrule.SchedulersTestRule
import com.example.notes.ui.Screens
import com.github.terrakok.cicerone.Router
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException

@RunWith(MockitoJUnitRunner::class)
class CreateNoteViewModelTest {

	@Rule
	@JvmField
	val schedulersTestRule = SchedulersTestRule()

	@Rule
	@JvmField
	val testExecutor = InstantTaskExecutorRule()

	private val createNoteUseCase: CreateNoteUseCase = mock()
	private val router: Router = mock()

	private val viewModel = CreateNoteViewModel(
		createNoteUseCase,
		router
	)

	private val messageObserver: Observer<String> = mock()

	private val note = Note(
		id = 0,
		description = "",
		title = ""
	)

	@Before
	fun setUp() {
		viewModel.message.observeForever(messageObserver)
	}

	@Test
	fun `createNote successful EXPECT router navigate to notes list screen`() {
		whenever(createNoteUseCase(note)).thenReturn(Completable.complete())

		viewModel.createNote()

		verify(router).navigateTo(Screens.NotesList)
	}

	@Test
	fun `createNote unsuccessful EXPECT message will be changed`() {
		val error = UnknownHostException()

		whenever(createNoteUseCase(note)).thenReturn(Completable.error(error))

		viewModel.createNote()

		verify(messageObserver).onChanged(error.localizedMessage)
	}
}