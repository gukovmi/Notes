package com.example.notes.data.datasource

import com.example.notes.data.db.NotesDao
import com.example.notes.data.model.NoteModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocalNoteDataSourceTest {

	private val db: NotesDao = mock()

	private val note = NoteModel(
		id = 1,
		description = "description1",
		title = "title1"
	)

	@Test
	fun `get notes EXPECT db get really notes`() {
		val expected = listOf(note)

		whenever(db.getNotes()).thenReturn(Single.just(expected))

		val result = db.getNotes().test()

		result.assertValue(expected)
	}

	@Test
	fun `get note by id EXPECT db get really note`() {
		val id: Long = 1
		val expected = note

		whenever(db.getNoteById(id)).thenReturn(Single.just(expected))

		val result = db.getNoteById(id).test()

		result.assertValue(expected)
	}

	@Test
	fun `create note EXPECT db return complete`() {
		val expected = Completable.complete()

		whenever(db.createNote(note)).thenReturn(expected)

		val result = db.createNote(note).test()

		result.assertComplete()
	}

	@Test
	fun `update note EXPECT db return complete`() {
		val expected = Completable.complete()

		whenever(db.updateNote(note)).thenReturn(expected)

		val result = db.updateNote(note).test()

		result.assertComplete()
	}

	@Test
	fun `delete note EXPECT db return complete`() {
		val expected = Completable.complete()

		whenever(db.deleteNote(note)).thenReturn(expected)

		val result = db.deleteNote(note).test()

		result.assertComplete()
	}
}