package android.example.justnote

class NoteRepository (private val db : NoteDatabase) {
    suspend fun insertNote (note:Note) = db.noteDao().insertNotes(note)
    suspend fun deleteNote(note:Note) = db.noteDao().deleteNote(note)
    suspend fun updateNote(note: Note) = db.noteDao().updateNote(note)
    fun getAllNotes() = db.noteDao().getAllNotes()
    fun searchNote(query:String?) = db.noteDao().searchNote(query)
    fun deleteAllNotes() = db.noteDao().deleteAll()
}