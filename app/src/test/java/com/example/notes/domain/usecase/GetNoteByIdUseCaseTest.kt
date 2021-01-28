package com.example.notes.domain.usecase

import com.example.notes.domain.entity.Note
import com.example.notes.domain.repository.NoteRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetNoteByIdUseCaseTest {

	private val noteRepository: NoteRepository = mock()

	private val useCase = GetNoteByIdUseCase(noteRepository)

	@Test
	fun `invoke EXPECT get note`() {
		val expected = Note(id = 1, title = "title1", description = "description1")

		whenever(noteRepository.getNoteById(expected.id)).thenReturn(Single.just(expected))

		val result = useCase(expected.id).test()

		result.assertValue(expected)
	}
}