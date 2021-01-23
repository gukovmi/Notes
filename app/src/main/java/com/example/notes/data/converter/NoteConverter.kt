package com.example.notes.data.converter

import com.example.notes.data.model.NoteModel
import com.example.notes.domain.entity.Note
import javax.inject.Inject

class NoteConverter @Inject constructor() {

	fun toNote(noteModel: NoteModel): Note =
		Note(
			id = noteModel.id,
			title = noteModel.title,
			description = noteModel.description
		)

	fun toNoteModel(note: Note) =
		NoteModel(
			id = note.id,
			title = note.title,
			description = note.description
		)
}