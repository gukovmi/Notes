package com.example.notes.presentation.state

sealed class NotesListState {

	object Default : NotesListState()

	object InProgress : NotesListState()

	data class Error(val throwable: Throwable) : NotesListState()
}
