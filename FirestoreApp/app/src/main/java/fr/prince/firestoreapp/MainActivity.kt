package fr.prince.firestoreapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import fr.prince.firestoreapp.classes.Note

class MainActivity : AppCompatActivity() {
    private lateinit var editTextTitle : EditText
    private lateinit var editTextDscription: EditText
    private lateinit var saveButton: Button
    private lateinit var title_updated_button : Button
    private val db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val docRef : DocumentReference = db.collection("Notebook").document("My fist document")
    private lateinit var loadButton : Button
    private lateinit var textViewData : TextView
    private lateinit var delete_description : Button
    private lateinit var delete_note : Button

    private val KEY_TITLE = "title"
    private val KEY_DESCRIPTION = "description"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextTitle = findViewById(R.id.edit_text_title)
        editTextDscription = findViewById(R.id.edit_text_description)
        saveButton = findViewById(R.id.button_save)
        loadButton = findViewById(R.id.load_button)
        textViewData = findViewById(R.id.text_view_data)
        title_updated_button = findViewById(R.id.button_update_title)
        delete_description = findViewById(R.id.delete_description)
        delete_note = findViewById(R.id.delete_note)

        saveButton.setOnClickListener {
            save()
        }
        title_updated_button.setOnClickListener {
            updateTitle()
        }

        delete_description.setOnClickListener {
            deleteDescription()
        }
        delete_note.setOnClickListener {
            deleteNote()
        }

        /*loadButton.setOnClickListener {
            loadData()
        }

         */
    }

    private fun deleteDescription() {
        val note = mutableMapOf<String, Any>()
        note[KEY_DESCRIPTION] = FieldValue.delete()

        docRef.update(note)
    }

    private fun deleteNote() {
        docRef.delete()
    }

    override fun onStart() {
        super.onStart()
        docRef.addSnapshotListener { document, error ->
            error?.let {
                return@addSnapshotListener
            }
            document?.let {
                if (it.exists()) {
//                    val title = document.getString(KEY_TITLE)
//                    val description = document.getString(KEY_DESCRIPTION)
//                    textViewData.text = "Text : $title\nDescription : $description"

                    // Ce simple code remplace le code commenter
                    val note = it.toObject(Note :: class.java)
                    textViewData.text = "Title : ${note?.title}\nDescription : ${note?.description}"
                } else {
                    textViewData.text = ""
                    Toast.makeText(this@MainActivity, "Error : The document does not  exist!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateTitle() {
        val title = editTextTitle.text.toString()
        val note = mutableMapOf<String, Any>()
        note[KEY_TITLE] = title

        //Modifier sans créer un nouvel item si l'item n'existe plus
        docRef.update(KEY_TITLE, title)
        //Modifier et créer un nouvel item si l'item n'existe plus
        //docRef.set(note, SetOptions.merge())
    }

    private fun save() {
        val title = editTextTitle.text.toString()
        val description = editTextDscription.text.toString()

       /* val note = mutableMapOf<String, Any>()
        note.put(KEY_TITLE, title)
        note.put(KEY_DESCRIPTION, description)*/

        // Ce simple code remplace le code commenter
        val note = Note(title, description)

        docRef.set(note)
            .addOnCompleteListener {
                Toast.makeText(this@MainActivity, "Note added!", Toast.LENGTH_SHORT).show()
                //title_text.text = title
                //description_text.text = description
            }
            .addOnFailureListener {
                Toast.makeText(this@MainActivity, "Error : note was not  added!", Toast.LENGTH_SHORT).show()
            }

    }



    /*
    private fun loadData() {
        docRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val title = document.getString(KEY_TITLE)
                    val description = document.getString(KEY_TITLE)
                    textViewData.text = "Text : $title\nDescription : $description"
                } else {
                    Toast.makeText(this@MainActivity, "Error : The document does not  exist!", Toast.LENGTH_SHORT).show()

                }
            }.addOnFailureListener {
                Toast.makeText(this@MainActivity, "Failed to load data", Toast.LENGTH_SHORT).show()

            }
    }
*/

}