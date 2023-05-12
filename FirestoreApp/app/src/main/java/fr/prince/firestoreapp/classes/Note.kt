package fr.prince.firestoreapp.classes

import com.google.firebase.firestore.Exclude

data class Note(val title : String, val description : String) {
    constructor() : this("", "") // public no-org constructor needed

    @Exclude
    var id: String = ""
}