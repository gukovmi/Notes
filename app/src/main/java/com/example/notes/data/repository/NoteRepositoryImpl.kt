package com.example.notes.data.repository

import com.example.notes.data.datasource.LocalNoteDataSource
import com.example.notes.data.model.NoteModel
import com.example.notes.domain.entity.Note
import com.example.notes.domain.repository.NoteRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
	private val localNoteDataSource: LocalNoteDataSource
) : NoteRepository {

	override fun getNotes(): Single<List<Note>> =
		localNoteDataSource.getNotes()
			.map { noteModels ->
				noteModels.map { noteModel ->
					noteModel.toNote()
				}
			}

	override fun createNote(note: Note): Completable =
		localNoteDataSource.createNote(note.toNoteModel())

	override fun getNoteById(id: Long): Single<Note> =
		localNoteDataSource.getNoteById(id)
			.map { note ->
				note.toNote()
			}

	override fun updateNote(note: Note): Completable =
		localNoteDataSource.updateNote(note.toNoteModel())

	override fun deleteNote(note: Note): Completable =
		localNoteDataSource.deleteNote(note.toNoteModel())

	private fun NoteModel.toNote() =
		Note(
			id = this.id,
			title = this.title,
			description = this.description
		)

	private fun Note.toNoteModel() =
		NoteModel(
			id = this.id,
			title = this.title,
			description = this.description
		)
}