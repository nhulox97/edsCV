package com.nhulox.edscv.controller

import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nhulox.edscv.MainActivity
import com.nhulox.edscv.NavBarMainActivity
import com.nhulox.edscv.R
import com.nhulox.edscv.model.User


/** * Created by nhulox97 on 02,mayo,2019 */
class FireAuth{
    private var auth: FirebaseAuth? = null
    private var fireStoreDB: FirebaseFirestore? = null
    private var context: Context? = null
    //private var taskStatus: Boolean = true

    constructor(context: Context){
        this.context = context
        auth = FirebaseAuth.getInstance()
        fireStoreDB = FirebaseFirestore.getInstance()
    }



    fun signInWithEmailAndPassword(email: String, password: String, displayName: String): Boolean{
        auth = FirebaseAuth.getInstance()
        fireStoreDB = FirebaseFirestore.getInstance()

        //Toast.makeText(context, "Test1", Toast.LENGTH_SHORT).show()
        val msgToast = context!!.resources.getString(R.string.AuthRegistered)

        auth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                Log.d("${R.string.AuthTAG}", "$msgToast")
                verifiedEmail(email)

                val registeredUser = auth!!.currentUser
                val user = User(registeredUser!!.uid, email, displayName).toMap()

                fireStoreDB!!.collection("users").document(registeredUser.uid)
                    .set(user).addOnSuccessListener{ _ ->
                    Log.d("${R.string.AuthTAG}", "DocumentSnapshot written with ID: ${registeredUser.uid}")

                    Toast.makeText(context, "$msgToast",
                        Toast.LENGTH_LONG).show()
                }

            }
            else{
                Log.w("${R.string.AuthTAG}", "createUserWithEmail:failure", task.exception)
                Toast.makeText(context, "${context!!.resources.getString(R.string.AuthError)}",
                    Toast.LENGTH_SHORT).show()
            }
        }

        return verifyCurrentUser()
    }

    fun logInWithEmailAndPassword(email: String, password: String):Boolean{
        auth = FirebaseAuth.getInstance()
        auth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("${R.string.AuthTAG}", "signInWithEmail:success")
                    val user = auth!!.currentUser
                    Toast.makeText(context, "${context!!.resources.getString(R.string.authSuccess)}",
                        Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(context, "${context!!.resources.getString(R.string.AuthError)}",
                        Toast.LENGTH_SHORT).show()
                }
            }
        return verifyCurrentUser()
    }

    fun verifyCurrentUser(): Boolean{
        return auth!!.currentUser != null
    }

    fun logOut(){
        auth!!.signOut()
    }

    private fun verifiedEmail(email: String?){
        //Code later
        //Toast.makeText(context, "Test2", Toast.LENGTH_LONG).show()
    }

}