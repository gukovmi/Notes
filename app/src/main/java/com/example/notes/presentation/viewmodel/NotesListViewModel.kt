package com.example.notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.domain.entity.Note
import com.example.notes.domain.usecase.DeleteNoteUseCase
import com.example.notes.domain.usecase.GetNotesUseCase
import com.example.notes.presentation.base.BaseViewModel
import com.example.notes.presentation.state.NotesListState
import com.example.notes.ui.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NotesListViewModel @Inject constructor(
	private val getNotesUseCase: GetNotesUseCase,
	private val deleteNoteUseCase: DeleteNoteUseCase,
	private val router: Router
) : BaseViewModel() {

	private val _notes: MutableLiveData<List<Note>> = MutableLiveData()
	val notes: LiveData<List<Note>> = _notes

	private val _state: MutableLiveData<NotesListState> = MutableLiveData()
	val state: LiveData<NotesListState> = _state

	fun navigateToNoteDetails(id: Long) {
		router.navigateTo(Screens.NoteDetails(id))
	}

	fun navigateToCreateNote() {
		router.navigateTo(Screens.CreateNote)
	}

	fun deleteNote(note: Note) {
		_state.value = NotesListState.InProgress
		deleteNoteUseCase(note).subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{
					_state.value = NotesListState.Default
					_notes.value = _notes.value?.filter { it != note }
				},
				::handleError
			).addToComposite()
	}

	fun loadNotes() {
		_state.value = NotesListState.InProgress
		getNotesUseCase()
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{ notes ->
					_state.value = NotesListState.Default
					_notes.value = notes
				},
				::handleError
			).addToComposite()
	}

	private fun handleError(throwable: Throwable) {
		_state.value = NotesListState.Error(throwable)
	}
}