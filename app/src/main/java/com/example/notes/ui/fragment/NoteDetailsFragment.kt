package com.example.notes.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.notes.App
import com.example.notes.R
import com.example.notes.domain.entity.Note
import com.example.notes.presentation.viewmodel.NoteDetailsViewModel
import com.example.notes.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_note_details.noteChangeDetailsButton
import kotlinx.android.synthetic.main.fragment_note_details.noteDescriptionDetailsEditText
import kotlinx.android.synthetic.main.fragment_note_details.noteTitleDetailsEditText
import javax.inject.Inject

class NoteDetailsFragment : BaseFragment() {

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

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_note_details, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		val noteId = arguments?.getLong("noteId")
		if (noteId != null) {
			initViews(noteId)
			viewModel.getNoteById(noteId)
		}
		initListeners()
	}

	private fun initViews(noteId: Long) {
		noteChangeDetailsButton.setOnClickListener {
			val title = noteTitleDetailsEditText.text.toString()
			val description = noteDescriptionDetailsEditText.text.toString()
			viewModel.updateNote(noteId, title, description)
		}
	}

	private fun initListeners() {
		viewModel.note.observe(this.viewLifecycleOwner, Observer<Note> { note ->
			noteTitleDetailsEditText.setText(note.title)
			noteDescriptionDetailsEditText.setText(note.description)
		})
		viewModel.message.observe(this.viewLifecycleOwner, Observer<String> { error ->
			showMessage(error)
		})
	}

	private fun showMessage(message: String) {
		Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
	}
}