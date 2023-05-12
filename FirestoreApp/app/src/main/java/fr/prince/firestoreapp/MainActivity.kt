package fr.prince.firestoreapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import fr.prince.firestoreapp.classes.Note

class MainActivity : AppCompatActivity() {
    private lateinit var editTextTitle : EditText
    private lateinit var editTextDscription: EditText
    private lateinit var saveButton: Button
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var loadNoteButton : Button
    private lateinit var textViewData : TextView
    private  var noteBookRef : CollectionReference = db.collection("Notebook")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextTitle = findViewById(R.id.edit_text_title)
        editTextDscription = findViewById(R.id.edit_text_description)
        saveButton = findViewById(R.id.button_save)
        loadNoteButton = findViewById(R.id.load_button)
        textViewData = findViewById(R.id.text_view_data)

        saveButton.setOnClickListener {
            addNote()
        }


        loadNoteButton.setOnClickListener {
            loadNote()
        }


    }

    override fun onStart() {
        super.onStart()
        noteBookRef.addSnapshotListener { documentSnapshoats, error ->
            error?.let {
                return@addSnapshotListener
            }
            documentSnapshoats?.let {
                var data = ""

          for (documentSnapshot in it) {
              val note = documentSnapshot.toObject(Note::class.java)
              note.id = documentSnapshot.id

              val title = note.title
              val description = note.description

              data = "ID : ${note.id} \nTitle : $title \nDescription : $description\n\n"
          }
                textViewData.text = data
            }
        }
    }

    private fun addNote() {
        val title = editTextTitle.text.toString()
        val description = editTextDscription.text.toString()

        val note = Note(title, description)

        noteBookRef.add(note)
            .addOnSuccessListener {
                Toast.makeText(this@MainActivity, "Note is added!!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this@MainActivity, "Error is happened!!", Toast.LENGTH_SHORT).show()

            }
    }
private fun loadNote() {
    noteBookRef.get()
        .addOnSuccessListener { queryDocumentsnapshoats ->
            var data = ""

            for (documentSnapshot in queryDocumentsnapshoats) {
                val note = documentSnapshot.toObject(Note ::class.java)

                val title = note.title
                val description = note.description
                data =  "Title : $title  \nDescription : $description"
            }
            textViewData.text = data
        }
}

}