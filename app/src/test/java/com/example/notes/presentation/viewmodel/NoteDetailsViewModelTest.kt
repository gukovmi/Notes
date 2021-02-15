package com.example.notes.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.notes.domain.entity.Note
import com.example.notes.domain.usecase.GetNoteByIdUseCase
import com.example.notes.domain.usecase.UpdateNoteUseCase
import com.example.notes.testrule.SchedulersTestRule
import com.example.notes.ui.Screens
import com.github.terrakok.cicerone.Router
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException

@RunWith(MockitoJUnitRunner::class)
class NoteDetailsViewModelTest {

	@Rule
	@JvmField
	val schedulersTestRule = SchedulersTestRule()

	@Rule
	@JvmField
	val testExecutor = InstantTaskExecutorRule()

	private val getNoteByIdUseCase: GetNoteByIdUseCase = mock()
	private val updateNoteUseCase: UpdateNoteUseCase = mock()
	private val router: Router = mock()

	private val viewModel = NoteDetailsViewModel(
		getNoteByIdUseCase,
		updateNoteUseCase,
		router
	)

	private val titleObserver: Observer<String> = mock()
	private val descriptionObserver: Observer<String> = mock()
	private val messageObserver: Observer<String> = mock()

	private val id = 0L
	private val note = Note(
		id = id,
		description = "",
		title = ""
	)

	@Before
	fun setUp() {
		viewModel.title.observeForever(titleObserver)
		viewModel.description.observeForever(descriptionObserver)
		viewModel.message.observeForever(messageObserver)
	}

	@Test
	fun `set noteId EXPECT description will be changed`() {
		whenever(getNoteByIdUseCase(id)).thenReturn(Single.just(note))

		viewModel.noteId = id

		verify(descriptionObserver).onChanged("")
	}

	@Test
	fun `set noteId EXPECT title will be changed`() {
		whenever(getNoteByIdUseCase(id)).thenReturn(Single.just(note))

		viewModel.noteId = id

		verify(titleObserver).onChanged("")
	}

	@Test
	fun `updateNote successful EXPECT router navigate to notes list screen`() {
		whenever(getNoteByIdUseCase(id)).thenReturn(Single.just(note))
		whenever(updateNoteUseCase(note)).thenReturn(Completable.complete())

		viewModel.noteId = id
		viewModel.updateNote()

		verify(router).navigateTo(Screens.NotesList)
	}

	@Test
	fun `updateNote unsuccessful EXPECT message will be changed`() {
		val error = UnknownHostException()

		whenever(getNoteByIdUseCase(id)).thenReturn(Single.just(note))
		whenever(updateNoteUseCase(note)).thenReturn(Completable.error(error))

		viewModel.noteId = id
		viewModel.updateNote()

		verify(messageObserver).onChanged(error.localizedMessage)
	}
}