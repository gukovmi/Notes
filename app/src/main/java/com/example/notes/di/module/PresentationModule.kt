package com.example.notes.di.module

//import com.example.notes.di.ViewModelKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.di.ViewModelKey
import com.example.notes.presentation.viewmodel.CreateNoteViewModel
import com.example.notes.presentation.viewmodel.MainViewModel
import com.example.notes.presentation.viewmodel.NoteDetailsViewModel
import com.example.notes.presentation.viewmodel.NotesListViewModel
import com.example.notes.ui.utils.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class PresentationModule {

	@Singleton
	@Binds
	abstract fun bindViewModelFactory(
		factory: ViewModelFactory
	): ViewModelProvider.Factory

	@Binds
	@IntoMap
	@ViewModelKey(MainViewModel::class)
	abstract fun bindMainViewModel(
		viewModel: MainViewModel
	): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(NotesListViewModel::class)
	abstract fun bindNotesListViewModel(
		viewModel: NotesListViewModel
	): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(CreateNoteViewModel::class)
	abstract fun bindCreateNoteViewModel(
		viewModel: CreateNoteViewModel
	): ViewModel

	@Binds
	@IntoMap
	@ViewModelKey(NoteDetailsViewModel::class)
	abstract fun bindNoteDetailsViewModel(
		viewModel: NoteDetailsViewModel
	): ViewModel
}