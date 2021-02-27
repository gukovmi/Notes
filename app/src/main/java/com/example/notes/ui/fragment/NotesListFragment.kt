package com.example.notes.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.App
import com.example.notes.R
import com.example.notes.domain.entity.Note
import com.example.notes.presentation.state.NotesListState
import com.example.notes.presentation.viewmodel.NotesListViewModel
import com.example.notes.ui.adapter.NotesListAdapter
import com.example.notes.ui.base.BaseFragment
import com.example.notes.ui.utils.subscribeSafe
import kotlinx.android.synthetic.main.fragment_notes_list.createNoteNotesListButton
import kotlinx.android.synthetic.main.fragment_notes_list.notesListRecyclerView
import javax.inject.Inject

class NotesListFragment : BaseFragment(R.layout.fragment_notes_list) {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	lateinit var viewModel: NotesListViewModel
	private lateinit var notesListAdapter: NotesListAdapter

	companion object {

		fun newInstance(): NotesListFragment =
			NotesListFragment()
	}

	override fun onAttach(context: Context) {
		App.component.injectNotesListFragment(this)
		viewModel = ViewModelProvider(this, viewModelFactory).get(NotesListViewModel::class.java)
		super.onAttach(context)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initViews(view)
		initAdapter()
		initListeners()

		if (viewModel.state.value == null) {
			viewModel.loadNotes()
		}
	}

	private fun initViews(view: View) {
		notesListRecyclerView.layoutManager = LinearLayoutManager(view.context)
		createNoteNotesListButton.setOnClickListener {
			viewModel.navigateToCreateNote()
		}
	}

	private fun initListeners() {
		viewModel.notes.observe(this.viewLifecycleOwner, notesListAdapter::showNotes)
		viewModel.state.subscribeSafe(this.viewLifecycleOwner, ::updateState)
	}

	private fun initAdapter() {
		notesListAdapter = NotesListAdapter(
			notesList = emptyList(),
			onNoteItemClick = { note: Note ->
				viewModel.navigateToNoteDetails(note.id)
			},
			onDeleteButtonClick = viewModel::deleteNote
		)
		notesListRecyclerView.adapter = notesListAdapter
	}

	private fun updateState(state: NotesListState) {
		when (state) {
			is NotesListState.Error -> showError(state.throwable)
		}
	}

	private fun showError(throwable: Throwable) {
		Toast.makeText(this.context, throwable.localizedMessage, Toast.LENGTH_LONG).show()
	}
}