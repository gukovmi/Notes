package com.example.notes.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.notes.App
import com.example.notes.R
import com.example.notes.presentation.viewmodel.CreateNoteViewModel
import com.example.notes.ui.base.BaseFragment
import com.example.notes.ui.utils.bind
import com.example.notes.ui.utils.subscribeSafe
import kotlinx.android.synthetic.main.fragment_create_note.createNoteCreateButton
import kotlinx.android.synthetic.main.fragment_create_note.noteDescriptionCreateEditText
import kotlinx.android.synthetic.main.fragment_create_note.noteTitleCreateEditText
import javax.inject.Inject

class CreateNoteFragment : BaseFragment(R.layout.fragment_create_note) {

	@Inject
	lateinit var viewModelFactory: ViewModelProvider.Factory
	lateinit var viewModel: CreateNoteViewModel

	companion object {

		fun newInstance(): CreateNoteFragment =
			CreateNoteFragment()
	}

	override fun onAttach(context: Context) {
		App.component.injectCreateNoteFragment(this)
		viewModel = ViewModelProvider(this, viewModelFactory).get(CreateNoteViewModel::class.java)
		super.onAttach(context)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initViews()
		initListeners()
	}

	private fun initViews() {
		createNoteCreateButton.setOnClickListener {
			viewModel.createNote()
		}
	}

	private fun initListeners() {
		viewModel.title.bind(this.viewLifecycleOwner, noteTitleCreateEditText)
		viewModel.description.bind(this.viewLifecycleOwner, noteDescriptionCreateEditText)
		viewModel.message.subscribeSafe(this.viewLifecycleOwner, ::showMessage)
	}

	private fun showMessage(message: String) {
		Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
	}
}