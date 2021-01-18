package com.example.notes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.data.model.NoteModel

@Database(entities = [(NoteModel::class)], version = 1, exportSchema = false)
internal abstract class AppDatabase : RoomDatabase() {

	abstract fun notesDao(): NotesDao
}