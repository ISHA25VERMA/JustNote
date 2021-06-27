package android.example.justnote

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NoteDao {
    @Query("SELECT * FROM NotesTable ORDER BY id DESC")
    fun getAllNotes() : LiveData<List<Note>>           //suspend because we want to use these functions in coroutines

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("DELETE FROM NotesTable")
    fun deleteAll()

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM NotesTable WHERE noteTitle LIKE :query OR noteBody LIKE:query")
    fun searchNote(query: String?): LiveData<List<Note>>
}