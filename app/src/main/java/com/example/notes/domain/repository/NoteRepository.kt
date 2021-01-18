package com.example.notes.domain.repository

import com.example.notes.domain.entity.Note
import io.reactivex.Completable
import io.reactivex.Single

interface NoteRepository {

	fun getNotes(): Single<List<Note>>
	fun createNote(note: Note): Completable
	fun getNoteById(id: Long): Single<Note>
	fun updateNote(note: Note): Completable
	fun deleteNote(note: Note): Completable
}