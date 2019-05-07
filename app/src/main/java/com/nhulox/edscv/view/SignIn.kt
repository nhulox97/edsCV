package com.nhulox.edscv.view


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nhulox.edscv.NavBarMainActivity

import com.nhulox.edscv.R
import com.nhulox.edscv.controller.FireAuth
import com.nhulox.edscv.model.User

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class SignIn() : Fragment() {

    private var button: Button? = null
    private var username: EditText? = null
    private var email: EditText? = null
    private var password: EditText? = null
    private var logInTV: TextView? = null

    private var txtUserName: String? = null
    private var txtEmail: String? = null
    private var txtPassword: String? = null

    private lateinit var fireAuth: FireAuth

    private var statusListener: UsersFragmentListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)

        button = view.findViewById(R.id.btnSignIn)
        username = view.findViewById(R.id.usernameS)
        email = view.findViewById(R.id.emailS)
        password = view.findViewById(R.id.passwordS)
        logInTV = view.findViewById(R.id.logInFragment)

        fireAuth = FireAuth(context!!)

        //Button onClick event
        button!!.setOnClickListener { registerUser() }

        logInTV!!.setOnClickListener { statusListener!!.getActualFragment("LogIn") }


        return view
    }


    private fun registerUser(){
        txtUserName = username!!.text.toString()
        txtEmail = email!!.text.toString()
        txtPassword = password!!.text.toString()

        if (!TextUtils.isEmpty(txtUserName) && !TextUtils.isEmpty(txtEmail) && !TextUtils.isEmpty(txtPassword)){
            if (fireAuth.signInWithEmailAndPassword(txtEmail!!, txtPassword!!, txtUserName!!)){
                val intent = Intent(context, NavBarMainActivity::class.java)
                startActivity(intent)
            }
        }else{
            Toast.makeText(context, "${context!!.resources.getString(R.string.emptyFields)}",
                Toast.LENGTH_SHORT).show()
        }

    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try{
            statusListener = context as UsersFragmentListener
        }catch (e: ClassCastException){
            throw java.lang.ClassCastException("$context you must implement the interface")
        }
    }

}
