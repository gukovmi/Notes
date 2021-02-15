package com.example.notes.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.notes.domain.entity.Note
import com.example.notes.domain.usecase.DeleteNoteUseCase
import com.example.notes.domain.usecase.GetNotesUseCase
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
class NotesListViewModelTest {

	@Rule
	@JvmField
	val schedulersTestRule = SchedulersTestRule()

	@Rule
	@JvmField
	val testExecutor = InstantTaskExecutorRule()

	private val getNotesUseCase: GetNotesUseCase = mock()
	private val deleteNoteUseCase: DeleteNoteUseCase = mock()
	private val router: Router = mock()

	private val viewModel = NotesListViewModel(
		getNotesUseCase,
		deleteNoteUseCase,
		router
	)

	private val notesObserver: Observer<List<Note>> = mock()
	private val messageObserver: Observer<String> = mock()

	private val id: Long = 55
	private val note = Note(
		id = id,
		description = "description",
		title = "title"
	)
	private val notesList = listOf(note)

	@Before
	fun setUp() {
		viewModel.notes.observeForever(notesObserver)
		viewModel.message.observeForever(messageObserver)
	}

	@Test
	fun `navigateToNoteDetails EXPECT router navigate to details screen`() {
		viewModel.navigateToNoteDetails(id)

		verify(router).navigateTo(Screens.NoteDetails(id))
	}

	@Test
	fun `navigateToCreateNote EXPECT router navigate to create screen`() {
		viewModel.navigateToCreateNote()

		verify(router).navigateTo(Screens.CreateNote)
	}

	@Test
	fun `deleteNote successful EXPECT notes will be changed`() {
		whenever(getNotesUseCase()).thenReturn(Single.just(notesList))
		whenever(deleteNoteUseCase(note)).thenReturn(Completable.complete())

		viewModel.loadNotes()
		viewModel.deleteNote(note)

		verify(notesObserver).onChanged(emptyList())
	}

	@Test
	fun `deleteNote unsuccessful EXPECT message will be changed`() {
		val error = UnknownHostException()

		whenever(deleteNoteUseCase(note)).thenReturn(Completable.error(error))

		viewModel.deleteNote(note)

		verify(messageObserver).onChanged(error.localizedMessage)
	}

	@Test
	fun `loadNotes successful EXPECT notes will be changed`() {
		whenever(getNotesUseCase()).thenReturn(Single.just(notesList))

		viewModel.loadNotes()

		verify(notesObserver).onChanged(notesList)
	}

	@Test
	fun `loadNotes unsuccessful EXPECT message will be changed`() {
		val error = UnknownHostException()

		whenever(getNotesUseCase()).thenReturn(Single.error(error))

		viewModel.loadNotes()

		verify(messageObserver).onChanged(error.localizedMessage)
	}
}