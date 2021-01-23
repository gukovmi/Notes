package com.example.notes.di.module

import android.content.Context
import androidx.room.Room
import com.example.notes.data.converter.NoteConverter
import com.example.notes.data.datasource.LocalNoteDataSource
import com.example.notes.data.datasource.LocalNoteDataSourceImpl
import com.example.notes.data.db.AppDatabase
import com.example.notes.data.db.NotesDao
import com.example.notes.data.repository.NoteRepositoryImpl
import com.example.notes.domain.repository.NoteRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

	@Singleton
	@Provides
	fun provideNoteRepository(
		localNoteDataSource: LocalNoteDataSource,
		noteConverter: NoteConverter
	): NoteRepository =
		NoteRepositoryImpl(localNoteDataSource, noteConverter)

	@Singleton
	@Provides
	fun provideLocalNoteDataSource(
		db: NotesDao
	): LocalNoteDataSource =
		LocalNoteDataSourceImpl(db)

	@Singleton
	@Provides
	fun provideNotesDao(context: Context): NotesDao =
		Room.databaseBuilder(
			context,
			AppDatabase::class.java,
			"notes"
		).build().notesDao()
}