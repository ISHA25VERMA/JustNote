package android.example.justnote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteViewModel (
    app: Application
    ,private val noteRepository: NoteRepository)
    : AndroidViewModel(app){
    fun addNote(note: Note) =
        viewModelScope.launch {
            noteRepository.insertNote(note)
        }

    fun deleteNote(note: Note) =
        viewModelScope.launch {
            noteRepository.deleteNote(note)
        }

    fun updateNote(note: Note) =
        viewModelScope.launch {
            noteRepository.updateNote(note)
        }

    fun getAllNote() = noteRepository.getAllNotes()

    fun deleteAllNotes() = noteRepository.deleteAllNotes()

    fun searchNote(query: String?) =
        noteRepository.searchNote(query)
}