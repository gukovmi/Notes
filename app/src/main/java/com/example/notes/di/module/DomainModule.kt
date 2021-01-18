package com.example.notes.di.module

import com.example.notes.domain.repository.NoteRepository
import com.example.notes.domain.usecase.CreateNoteUseCase
import com.example.notes.domain.usecase.DeleteNoteUseCase
import com.example.notes.domain.usecase.GetNoteByIdUseCase
import com.example.notes.domain.usecase.GetNotesUseCase
import com.example.notes.domain.usecase.UpdateNoteUseCase
import dagger.Module
import dagger.Provides

@Module
class DomainModule {

	@Provides
	fun provideGetNotesUseCase(noteRepository: NoteRepository): GetNotesUseCase =
		GetNotesUseCase(noteRepository)

	@Provides
	fun provideCreateNoteUseCase(noteRepository: NoteRepository): CreateNoteUseCase =
		CreateNoteUseCase(noteRepository)

	@Provides
	fun provideGetNoteByIdUseCase(noteRepository: NoteRepository): GetNoteByIdUseCase =
		GetNoteByIdUseCase(noteRepository)

	@Provides
	fun provideUpdateNoteUseCase(noteRepository: NoteRepository): UpdateNoteUseCase =
		UpdateNoteUseCase(noteRepository)

	@Provides
	fun provideDeleteNoteUseCase(noteRepository: NoteRepository): DeleteNoteUseCase =
		DeleteNoteUseCase(noteRepository)
}