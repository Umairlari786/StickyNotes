package com.example.stickynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), INotesRVAdapter {

    lateinit var viewModel: NoteViewModel
    lateinit var inputnote:EditText
    lateinit var recyclerView:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView=findViewById(R.id.recyclerview)

        inputnote=findViewById(R.id.inputnote)

        recyclerView.layoutManager=LinearLayoutManager(this)
        val adapter=NoteRvAdapter(this,this)
        recyclerView.adapter=adapter

        viewModel=ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)

       viewModel.allNote.observe(this, Observer{list->
           list?.let{
               adapter.updateList(it)
           }



       })


    }

    override fun onItemClicked(notes: Note) {
        viewModel.deleteNote(notes)
        Toast.makeText(this,"${notes.text} Deleted",Toast.LENGTH_LONG).show()

    }

    fun submitData(view: View) {
        val noteText = inputnote.text.toString()
        if (noteText.isNotEmpty()){
            viewModel.insertNote(Note(noteText))
            Toast.makeText(this,"$noteText inserted",Toast.LENGTH_LONG).show()

        }
    }


}