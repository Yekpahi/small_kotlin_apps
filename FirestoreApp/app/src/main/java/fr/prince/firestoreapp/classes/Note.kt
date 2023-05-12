package fr.prince.firestoreapp.classes

data class Note(val title : String, val description : String) {
    constructor() : this("", "") // public no-org constructor needed
}