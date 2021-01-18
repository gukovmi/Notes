package com.example.notes.ui

import com.example.notes.ui.fragment.CreateNoteFragment
import com.example.notes.ui.fragment.NoteDetailsFragment
import com.example.notes.ui.fragment.NotesListFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {

	fun notesList() = FragmentScreen { NotesListFragment.newInstance() }
	fun noteDetails(id: Long) = FragmentScreen { NoteDetailsFragment.newInstance(id) }
	fun createNote() = FragmentScreen { CreateNoteFragment.newInstance() }
}