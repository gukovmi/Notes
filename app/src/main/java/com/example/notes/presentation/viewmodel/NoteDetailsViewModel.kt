package com.example.notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.domain.entity.Note
import com.example.notes.domain.usecase.GetNoteByIdUseCase
import com.example.notes.domain.usecase.UpdateNoteUseCase
import com.example.notes.presentation.base.BaseViewModel
import com.example.notes.ui.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NoteDetailsViewModel @Inject constructor(
	private val getNoteByIdUseCase: GetNoteByIdUseCase,
	private val updateNoteUseCase: UpdateNoteUseCase,
	private val router: Router
) : BaseViewModel() {

	private val _note: MutableLiveData<Note> = MutableLiveData()
	val note: LiveData<Note> = _note

	private val _message: MutableLiveData<String> = MutableLiveData()
	val message: LiveData<String> = _message

	fun getNoteById(id: Long) {
		getNoteByIdUseCase(id).subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ note ->
						   _note.value = note
					   }, { error ->
						   _message.value = error.localizedMessage
					   }).addToComposite()
	}

	fun updateNote(id: Long, title: String, description: String) {
		val note = Note(id, title, description)
		updateNoteUseCase(note).subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({
						   router.navigateTo(Screens.notesList())
					   },
					   { error ->
						   _message.value = error.localizedMessage
					   }).addToComposite()
	}
}