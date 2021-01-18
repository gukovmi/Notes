package com.example.notes.data.datasource

import com.example.notes.data.db.NotesDao
import com.example.notes.data.model.NoteModel
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface LocalNoteDataSource {

	fun getNotes(): Single<List<NoteModel>>
	fun getNoteById(id: Long): Single<NoteModel>
	fun createNote(note: NoteModel): Completable
	fun updateNote(note: NoteModel): Completable
	fun deleteNote(note: NoteModel): Completable
}

class LocalNoteDataSourceImpl @Inject constructor(
	private val db: NotesDao
) : LocalNoteDataSource {

	override fun getNotes(): Single<List<NoteModel>> =
		db.getNotes()

	override fun getNoteById(id: Long): Single<NoteModel> =
		db.getNoteById(id)

	override fun createNote(note: NoteModel): Completable =
		db.createNote(note)

	override fun updateNote(note: NoteModel): Completable =
		db.updateNote(note)

	override fun deleteNote(note: NoteModel): Completable =
		db.deleteNote(note)
}