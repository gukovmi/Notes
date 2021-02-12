package com.example.notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.domain.entity.Note
import com.example.notes.domain.usecase.GetNoteByIdUseCase
import com.example.notes.domain.usecase.UpdateNoteUseCase
import com.example.notes.presentation.base.BaseViewModel
import com.example.notes.ui.Screens
import com.example.notes.ui.utils.SingleLiveEvent
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NoteDetailsViewModel @Inject constructor(
	private val getNoteByIdUseCase: GetNoteByIdUseCase,
	private val updateNoteUseCase: UpdateNoteUseCase,
	private val router: Router
) : BaseViewModel() {

	var noteId: Long? = null
		set(value) {
			if (field == value) return
			field = value
			field?.let { getNoteById(it) }
		}

	val title: MutableLiveData<String> = MutableLiveData()
	val description: MutableLiveData<String> = MutableLiveData()

	private val _message: SingleLiveEvent<String> = SingleLiveEvent()
	val message: LiveData<String> = _message

	private fun getNoteById(id: Long) {
		getNoteByIdUseCase(id).subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({ note ->
						   description.value = note.description
						   title.value = note.title
					   }, { error ->
						   _message.value = error.localizedMessage
					   }).addToComposite()
	}

	fun updateNote() {
		noteId?.let {
			val note = Note(
				id = it,
				title = this.title.value ?: "",
				description = this.description.value ?: ""
			)
			updateNoteUseCase(note).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe({
							   router.navigateTo(Screens.NotesList)
						   },
						   { error ->
							   _message.value = error.localizedMessage
						   }).addToComposite()
		}
	}
}