package com.example.notes.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notes.domain.entity.Note
import com.example.notes.domain.usecase.CreateNoteUseCase
import com.example.notes.presentation.base.BaseViewModel
import com.example.notes.ui.Screens
import com.github.terrakok.cicerone.Router
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CreateNoteViewModel @Inject constructor(
	private val createNoteUseCase: CreateNoteUseCase,
	private val router: Router
) : BaseViewModel() {

	private val _message: MutableLiveData<String> = MutableLiveData()
	val message: LiveData<String> = _message

	fun createNote(title: String, description: String) {
		val note = Note(
			title = title,
			description = description
		)
		createNoteUseCase(note)
			.subscribeOn(Schedulers.io())
			.observeOn(AndroidSchedulers.mainThread())
			.subscribe({
						   router.navigateTo(Screens.notesList())
					   },
					   { error ->
						   _message.value = error.localizedMessage
					   }).addToComposite()
	}
}