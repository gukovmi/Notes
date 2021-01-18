package com.example.notes.data.repository

import com.example.notes.data.datasource.LocalNoteDataSource
import com.example.notes.data.model.NoteModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NoteRepositoryTest {

	private val localNoteDataSource: LocalNoteDataSource = mock()

	private val note = NoteModel(
		id = 1,
		description = "description1",
		title = "title1"
	)

	@Test
	fun `get notes EXPECT local data source get really notes`() {
		val expected = listOf(note)

		whenever(localNoteDataSource.getNotes()).thenReturn(Single.just(expected))

		val result = localNoteDataSource.getNotes().test()

		result.assertValue(expected)
	}

	@Test
	fun `get note by id EXPECT local data source get really note`() {
		val id: Long = 1
		val expected = note

		whenever(localNoteDataSource.getNoteById(id)).thenReturn(Single.just(expected))

		val result = localNoteDataSource.getNoteById(id).test()

		result.assertValue(expected)
	}

	@Test
	fun `create note EXPECT local data source return complete`() {
		val expected = Completable.complete()

		whenever(localNoteDataSource.createNote(note)).thenReturn(expected)

		val result = localNoteDataSource.createNote(note).test()

		result.assertComplete()
	}

	@Test
	fun `update note EXPECT local data source return complete`() {
		val expected = Completable.complete()

		whenever(localNoteDataSource.updateNote(note)).thenReturn(expected)

		val result = localNoteDataSource.updateNote(note).test()

		result.assertComplete()
	}

	@Test
	fun `delete note EXPECT local data source return complete`() {
		val expected = Completable.complete()

		whenever(localNoteDataSource.deleteNote(note)).thenReturn(expected)

		val result = localNoteDataSource.deleteNote(note).test()

		result.assertComplete()
	}
}
