package com.example.notes.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.notes.data.model.NoteModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface NotesDao {

	@Query("SELECT * FROM notes ORDER BY id DESC")
	fun getNotes(): Single<List<NoteModel>>

	@Query("SELECT * FROM notes WHERE id LIKE :id")
	fun getNoteById(id: Long): Single<NoteModel>

	@Insert
	fun createNote(note: NoteModel): Completable

	@Update
	fun updateNote(note: NoteModel): Completable

	@Delete
	fun deleteNote(note: NoteModel): Completable
}