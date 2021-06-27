package android.example.justnote

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.VersionedParcelize
import kotlinx.parcelize.Parcelize

@Entity(tableName = "NotesTable")
@Parcelize
data class Note(
        @PrimaryKey(autoGenerate = true)
        val id: Int,
        val noteTitle: String,
        val noteBody: String,
        val noteSubTitle: String,
        val DateTime: String,
        val color: String
) : Parcelable
