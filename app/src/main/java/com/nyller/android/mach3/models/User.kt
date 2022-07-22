package com.nyller.android.mach3.models

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable

data class User(
    var nome : String?= null,
    var email : String?= null,
    var senha : String?= null,
    var id : String?= null
) : Serializable

fun saveInDb(user : User){
    val db = Firebase.firestore

    val userRef = FirebaseAuth.getInstance()
    val usuarioAtual = userRef.currentUser?.uid

    usuarioAtual?.let { idUsuario ->
        db.collection("usuarios")
            .document(idUsuario).set(user)
        .addOnCompleteListener {
            Log.i("Edu", "Sucesso ao cadastrar Usuario no DB")
        }.addOnFailureListener {
            Log.i("Edu", "Erro ao cadastrar usuario no DB")
        }
    }
}
