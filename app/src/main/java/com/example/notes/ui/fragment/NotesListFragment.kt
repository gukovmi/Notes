package com.example.notes.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.App
import com.example.notes.R
import com.example.notes.domain.entity.Note
import com.example.notes.presentation.viewmodel.NotesListViewModel
import com.example.notes.ui.adapter.NotesListAdapter
import com.example.notes.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_notes_list.createNoteNotesListButton
import kotlinx.android.synthetic.main.fragment_notes_list.notesListRecyclerView
import javax.inject.Inject

class NotesListFragment : BaseFragment() {

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

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_notes_list, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initViews(view)
		initAdapter()
		initListeners()
	}

	private fun initViews(view: View) {
		notesListRecyclerView.layoutManager = LinearLayoutManager(view.context)
		createNoteNotesListButton.setOnClickListener {
			viewModel.navigateToCreateNote()
		}
	}

	private fun initListeners() {
		viewModel.notes.observe(this.viewLifecycleOwner, Observer<List<Note>> { notesList ->
			notesListAdapter.showNotes(notesList)
		})
		viewModel.message.observe(this.viewLifecycleOwner, Observer<String> { error ->
			showMessage(error)
		})
	}

	private fun initAdapter() {
		notesListAdapter = NotesListAdapter(
			notesList = emptyList(),
			onNoteItemClick = { note: Note ->
				viewModel.navigateToNoteDetails(note.id)
			},
			onDeleteButtonClick = { note ->
				viewModel.deleteNote(note)
			})
		notesListRecyclerView.adapter = notesListAdapter
	}

	private fun showMessage(message: String) {
		Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
	}
}