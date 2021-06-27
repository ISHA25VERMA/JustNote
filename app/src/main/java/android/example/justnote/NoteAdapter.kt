package android.example.justnote

import android.example.justnote.databinding.NoteItemViewBinding
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.random.Random as Random1

class NoteAdapter  : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val differCallback =
        object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem.id == newItem.id &&
                        oldItem.noteBody == newItem.noteBody &&
                        oldItem.noteTitle == newItem.noteTitle &&
                        oldItem.noteSubTitle == newItem.noteSubTitle
            }

            override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem == newItem
            }

        }

    val differ = AsyncListDiffer(this, differCallback)

    class NoteViewHolder(val itemBinding: NoteItemViewBinding):
            RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemBinding.tvTitle.text =currentNote.noteTitle
        holder.itemBinding.textDesc.text = currentNote.noteBody
        holder.itemBinding.timeDate.text = currentNote.DateTime

        holder.itemBinding.cardView.setCardBackgroundColor(Color.parseColor(currentNote.color))

//        holder.itemBinding.cardView.setCardBackgroundColor(currentNote.color)
//        holder.itemView.setBackgroundColor(currentNote.color)
//        holder.itemBinding.rootView.setBackgroundColor(currentNote.color)
//        holder.itemBinding.root.setBackgroundColor(currentNote.color)

        holder.itemView.setOnClickListener{
            val direction = homeDirections.actionHomeFragmentToUpdateNoteFragment(currentNote)
            it.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

}