package com.example.notes.domain.usecase

import com.example.notes.domain.entity.Note
import com.example.notes.domain.repository.NoteRepository
import io.reactivex.Single
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
	private val noteRepository: NoteRepository
) {

	operator fun invoke(): Single<List<Note>> =
		noteRepository.getNotes()
}