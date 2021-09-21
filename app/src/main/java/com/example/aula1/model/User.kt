package com.example.aula1.model

import java.io.Serializable
import com.google.firebase.firestore.Exclude

data class User constructor(var uid: String, var name: String? = "", var email: String? = "", @Exclude var isNew: Boolean = false) : Serializable {
    companion object {
        val REF_NAME = "users"
    }
}