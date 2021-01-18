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
import com.example.notes.presentation.viewmodel.CreateNoteViewModel
import com.example.notes.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_create_note.createNoteCreateButton
import kotlinx.android.synthetic.main.fragment_create_note.noteDescriptionCreateEditText
import kotlinx.android.synthetic.main.fragment_create_note.noteTitleCreateEditText
import javax.inject.Inject

class CreateNoteFragment : BaseFragment() {

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

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		return inflater.inflate(R.layout.fragment_create_note, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		initViews()
		initListeners()
	}

	private fun initViews() {
		createNoteCreateButton.setOnClickListener {
			val title: String = noteTitleCreateEditText.text.toString()
			val description: String = noteDescriptionCreateEditText.text.toString()
			viewModel.createNote(title, description)
		}
	}

	private fun initListeners() {
		viewModel.message.observe(this.viewLifecycleOwner, Observer<String> { error ->
			showMessage(error)
		})
	}

	private fun showMessage(message: String) {
		Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
	}
}