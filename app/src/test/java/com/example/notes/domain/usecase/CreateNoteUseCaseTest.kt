package com.example.notes.domain.usecase

import com.example.notes.domain.entity.Note
import com.example.notes.domain.repository.NoteRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CreateNoteUseCaseTest {

	private val noteRepository: NoteRepository = mock()

	private val useCase = CreateNoteUseCase(noteRepository)

	@Test
	fun `invoke EXPECT complete`() {
		val note = Note(title = "title1", description = "description1")
		val expected = Completable.complete()

		whenever(noteRepository.createNote(note)).thenReturn(expected)

		val result = useCase(note).test()

		result.assertComplete()
	}
}