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
    private var context: Context? = null
    //private var taskStatus: Boolean = true

    constructor(context: Context){
        this.context = context
        auth = FirebaseAuth.getInstance()
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