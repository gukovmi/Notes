package com.example.notes.di.component

import com.example.notes.di.module.ContextModule
import com.example.notes.di.module.DataModule
import com.example.notes.di.module.DomainModule
import com.example.notes.di.module.NavigationModule
import com.example.notes.di.module.PresentationModule
import com.example.notes.ui.activity.MainActivity
import com.example.notes.ui.fragment.CreateNoteFragment
import com.example.notes.ui.fragment.NoteDetailsFragment
import com.example.notes.ui.fragment.NotesListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
	modules = [ContextModule::class,
		DataModule::class,
		DomainModule::class,
		PresentationModule::class,
		NavigationModule::class]
)
interface AppComponent {

	fun injectMainActivity(mainActivity: MainActivity)
	fun injectNotesListFragment(notesListFragment: NotesListFragment)
	fun injectNoteDetailsFragment(noteDetailsFragment: NoteDetailsFragment)
	fun injectCreateNoteFragment(createNoteFragment: CreateNoteFragment)
}