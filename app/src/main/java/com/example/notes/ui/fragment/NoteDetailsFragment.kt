package com.example.notes.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notes.App
import com.example.notes.R
import com.example.notes.presentation.viewmodel.NoteDetailsViewModel
import com.example.notes.ui.base.BaseFragment
import com.example.notes.ui.utils.bind
import com.example.notes.ui.utils.subscribeSafe
import kotlinx.android.synthetic.main.fragment_note_details.noteChangeDetailsButton
import kotlinx.android.synthetic.main.fragment_note_details.noteDescriptionDetailsEditText
import kotlinx.android.synthetic.main.fragment_note_details.noteTitleDetailsEditText
import javax.inject.Inject

class NoteDetailsFragment : BaseFragment(R.layout.fragment_note_details) {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	lateinit var viewModel: NoteDetailsViewModel

	companion object {

		fun newInstance(id: Long): NoteDetailsFragment =
			NoteDetailsFragment().apply {
				arguments = Bundle().apply {
					putLong("noteId", id)
				}
			}
	}

	override fun onAttach(context: Context) {
		App.component.injectNoteDetailsFragment(this)
		viewModel = ViewModelProvider(this, viewModelFactory).get(NoteDetailsViewModel::class.java)
		super.onAttach(context)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val noteId = arguments?.getLong("noteId")
		if (noteId != null) {
			viewModel.noteId = noteId
		}

		initViews()
		initListeners()
	}

	private fun initViews() {
		noteChangeDetailsButton.setOnClickListener {
			viewModel.updateNote()
		}
	}

	private fun initListeners() {
		viewModel.title.bind(this.viewLifecycleOwner, noteTitleDetailsEditText)
		viewModel.description.bind(this.viewLifecycleOwner, noteDescriptionDetailsEditText)
		viewModel.message.subscribeSafe(this.viewLifecycleOwner, { error ->
			showMessage(error)
		})
	}

	private fun showMessage(message: String) {
		Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
	}
}