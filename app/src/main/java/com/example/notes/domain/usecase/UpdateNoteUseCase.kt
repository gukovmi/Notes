package com.example.notes.domain.usecase

import com.example.notes.domain.entity.Note
import com.example.notes.domain.repository.NoteRepository
import io.reactivex.Completable
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
	private val noteRepository: NoteRepository
) {

	operator fun invoke(note: Note): Completable =
		noteRepository.updateNote(note)
}