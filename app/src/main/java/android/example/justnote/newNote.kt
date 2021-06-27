package android.example.justnote

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.example.justnote.databinding.FragmentNewNoteBinding
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.testing.FragmentScenario.Companion.launch
import androidx.lifecycle.LifecycleOwner
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import android.content.BroadcastReceiver as BroadcastReceiver1


class newNote : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private var _binding :FragmentNewNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView: View


    var noteId = -1

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
        _binding = FragmentNewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var selectedColor  = "#4e33ff"


        sharedViewModel.color.observe(viewLifecycleOwner, {
            it.also { selectedColor = it }
            binding.colorView.setBackgroundColor(Color.parseColor(it))
        })


        binding.bottom.setOnClickListener{
            val bottomSheet:BottomSheet = BottomSheet.newInstance(this.noteId)
            bottomSheet.show(requireActivity().supportFragmentManager,"note bottom sheet fragment")
        }

        binding.colorView.setOnClickListener{
            val bottomSheet:BottomSheet = BottomSheet.newInstance(this.noteId)
            bottomSheet.show(requireActivity().supportFragmentManager,"note bottom sheet fragment")
        }

        binding.check.setOnClickListener{
            saveNote(it, selectedColor)
        }

        binding.back.setOnClickListener{
            Snackbar.make(
                    view, "Note not saved",
                    Snackbar.LENGTH_SHORT
            ).show()
            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)
        }

        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        currentDate = sdf.format(Date())
        binding.dateTime.text = currentDate

        noteViewModel = (activity as MainActivity).noteViewModel
        mView = view
    }

    private fun saveNote(view : View, color: String){
        val noteTitle =binding.notesTitle.text.toString().trim()
        val noteSubtitle = binding.notesSubTitle.text.toString().trim()
        val description = binding.notesDescription.text.toString().trim()
        val dateTime = binding.dateTime.text.toString()

        if (noteTitle.isNotEmpty()) {
            val note = Note(0, noteTitle, description,noteSubtitle,dateTime, color)
            noteViewModel.addNote(note)
            Snackbar.make(
                    view, "Note saved successfully",
                    Snackbar.LENGTH_SHORT
            ).show()
            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)

        } else {
            activity?.toast("Please enter note title")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

