package com.example.notes.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.R
import com.example.notes.domain.entity.Note
import kotlinx.android.synthetic.main.item_note.view.noteDeleteItemButton
import kotlinx.android.synthetic.main.item_note.view.noteDescriptionItemTextView
import kotlinx.android.synthetic.main.item_note.view.noteTitleItemTextView

typealias OnNoteItemClick = (Note) -> Unit
typealias OnDeleteButtonClick = (Note) -> Unit

class NotesListAdapter(
	private var notesList: List<Note>,
	private val onNoteItemClick: OnNoteItemClick,
	private val onDeleteButtonClick: OnDeleteButtonClick
) : RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

		fun bindNote(
			note: Note,
			onNoteItemClick: OnNoteItemClick,
			onDeleteButtonClick: OnDeleteButtonClick
		) {
			itemView.apply {
				noteTitleItemTextView.text = note.title
				noteDescriptionItemTextView.text = note.description
				setOnClickListener { onNoteItemClick(note) }
				noteDeleteItemButton.setOnClickListener { onDeleteButtonClick(note) }
			}
		}
	}

	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): ViewHolder {
		val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
		return ViewHolder(view)
	}

	override fun getItemCount(): Int {
		return notesList.size
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		holder.bindNote(notesList[position], onNoteItemClick, onDeleteButtonClick)
	}

	fun showNotes(notesList: List<Note>) {
		this.notesList = notesList
		notifyDataSetChanged()
	}
}