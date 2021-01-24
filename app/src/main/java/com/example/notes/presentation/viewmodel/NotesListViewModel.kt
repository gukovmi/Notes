package com.example.notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.domain.entity.Note
import com.example.notes.domain.usecase.DeleteNoteUseCase
import com.example.notes.domain.usecase.GetNotesUseCase
import com.example.notes.presentation.base.BaseViewModel
import com.example.notes.ui.Screens
import com.example.notes.ui.utils.SingleLiveEvent
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

	private val _message: SingleLiveEvent<String> = SingleLiveEvent()
	val message: LiveData<String> = _message

	init {
		getNotes()
	}

	fun navigateToNoteDetails(id: Long) {
		router.navigateTo(Screens.noteDetails(id))
	}

	fun navigateToCreateNote() {
		router.navigateTo(Screens.createNote())
	}

	fun deleteNote(note: Note) {
		deleteNoteUseCase(note).subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({
						   getNotes()
					   },
					   { error ->
						   _message.value = error.localizedMessage
					   }).addToComposite()
	}

	private fun getNotes() {
		getNotesUseCase()
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe(
				{ notes ->
					_notes.value = notes
				},
				{ error ->
					_message.value = error.localizedMessage
				}
			).addToComposite()
	}
}