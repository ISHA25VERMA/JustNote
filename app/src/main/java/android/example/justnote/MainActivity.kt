package android.example.justnote

import android.example.justnote.databinding.ActivityMainBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setSupportActionBar(binding.toolbar)

        setUpViewModel()

    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(
                NoteDatabase(this)
        )

        val viewModelProviderFactory =
                ViewModelFactory(
                        application, noteRepository
                )

        noteViewModel = ViewModelProvider(
                this,
                viewModelProviderFactory
        ).get(NoteViewModel::class.java)
    }

    override fun onBackPressed() {
        finishAffinity()
    }

}