package android.example.justnote

import android.app.AlertDialog
import android.example.justnote.databinding.FragmentUpdateNoteBinding
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


class UpdateNote : Fragment() {
    // TODO: Rename and change types of parameters

    private val sharedViewModel: SharedViewModel by activityViewModels()

    var noteId = -1


    private var _binding: FragmentUpdateNoteBinding? = null
    private val binding get() = _binding!!

    private val args: UpdateNoteArgs by navArgs()
    private lateinit var currentNote: Note
    private lateinit var noteViewModel: NoteViewModel

    var selectedColor = "#4e33ff"


    var currentDate:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateNoteBinding.inflate(
                inflater,
                container,
                false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.colorViewUpdate.setOnClickListener{
            val bottomSheet:BottomSheet = BottomSheet.newInstance(this.noteId)
            bottomSheet.show(requireActivity().supportFragmentManager,"note bottom sheet fragment")
        }

        noteViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!

        sharedViewModel.color.observe(viewLifecycleOwner, {
            binding.colorViewUpdate.setBackgroundColor(Color.parseColor(it))
            selectedColor = it
        })

        selectedColor = currentNote.color

        binding.notesDescriptionUpdate.setText(currentNote.noteBody)
        binding.notesTitleUpdate.setText(currentNote.noteTitle)
        binding.notesSubTitleUpdate.setText(currentNote.noteSubTitle)
        binding.colorViewUpdate.setBackgroundColor(Color.parseColor(currentNote.color))


        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        binding.dateTimeUpdate.text = currentDate

        binding.bottomUpdate.setOnClickListener{
            val bottomSheet:BottomSheet = BottomSheet.newInstance(this.noteId)
            bottomSheet.show(requireActivity().supportFragmentManager,"note bottom sheet fragment")
        }

        binding.backUpdate.setOnClickListener{
            Snackbar.make(
                    view, "Note data not saved",
                    Snackbar.LENGTH_SHORT
            ).show()
            view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
        }

        binding.checkUpdate.setOnClickListener {
            updateNote(it, selectedColor)
        }

        binding.layoutDeleteNoteUpdate.setOnClickListener{
            AlertDialog.Builder(activity).apply {
                setTitle("Delete Note")
                setMessage("Are you sure you want to permanently delete this note?")
                setPositiveButton("DELETE") { _, _ ->
                    noteViewModel.deleteNote(currentNote)
                    view?.findNavController()?.navigate(
                            R.id.action_updateNoteFragment_to_homeFragment
                    )
                }
                setNegativeButton("CANCEL", null)
            }.create().show()
        }
    }

    private fun updateNote(view: View, color: String){

        val noteTitle = binding.notesTitleUpdate.text.toString().trim()
        val noteSubtitle = binding.notesSubTitleUpdate.text.toString().trim()
        val description = binding.notesDescriptionUpdate.text.toString().trim()
        val dateTime = binding.dateTimeUpdate.text.toString()

        if (noteTitle.isNotEmpty()) {

            val note = Note(currentNote.id, noteTitle, description, noteSubtitle, dateTime,color)
            noteViewModel.updateNote(note)
            Snackbar.make(
                    view, "Note saved successfully",
                    Snackbar.LENGTH_SHORT
            ).show()
            view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
        } else {
            activity?.toast("Please enter note title")
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}