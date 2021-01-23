package com.example.notes.data.repository

import com.example.notes.data.converter.NoteConverter
import com.example.notes.data.datasource.LocalNoteDataSource
import com.example.notes.domain.entity.Note
import com.example.notes.domain.repository.NoteRepository
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
	private val localNoteDataSource: LocalNoteDataSource,
	private val noteConverter: NoteConverter
) : NoteRepository {

	override fun getNotes(): Single<List<Note>> =
		localNoteDataSource.getNotes()
			.map { noteModels ->
				noteModels.map { noteModel ->
					noteConverter.toNote(noteModel)
				}
			}

	override fun createNote(note: Note): Completable =
		localNoteDataSource.createNote(noteConverter.toNoteModel(note))

	override fun getNoteById(id: Long): Single<Note> =
		localNoteDataSource.getNoteById(id)
			.map { noteModel ->
				noteConverter.toNote(noteModel)
			}

	override fun updateNote(note: Note): Completable =
		localNoteDataSource.updateNote(noteConverter.toNoteModel(note))

	override fun deleteNote(note: Note): Completable =
		localNoteDataSource.deleteNote(noteConverter.toNoteModel(note))
}