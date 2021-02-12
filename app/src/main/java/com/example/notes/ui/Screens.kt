package com.example.notes.ui

import com.example.notes.ui.fragment.CreateNoteFragment
import com.example.notes.ui.fragment.NoteDetailsFragment
import com.example.notes.ui.fragment.NotesListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

	data class NoteDetails(private val id: Long) : FragmentScreen(fragmentCreator = { NoteDetailsFragment.newInstance(id) })

	object NotesList : FragmentScreen(fragmentCreator = { NotesListFragment.newInstance() })
	object CreateNote : FragmentScreen(fragmentCreator = { CreateNoteFragment.newInstance() })
}