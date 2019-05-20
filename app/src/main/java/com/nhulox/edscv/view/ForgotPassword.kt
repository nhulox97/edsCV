package com.nhulox.edscv.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

import com.nhulox.edscv.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ForgotPassword() : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var emailET: EditText? = null
    private var logInTV: TextView? = null
    private var sendEmailFPButton: Button? = null
    private var statusListener: UsersFragmentListener? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view  = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        logInTV = view.findViewById(R.id.forgotPassword)
        sendEmailFPButton = view.findViewById(R.id.btnForgot)
        emailET = view.findViewById(R.id.emailL)

        auth = FirebaseAuth.getInstance()

        sendEmailFPButton!!.setOnClickListener { sendEmailRecoveryPassword(emailET!!.text.toString()) }
        logInTV!!.setOnClickListener { statusListener!!.getActualFragment("LogIn") }

        return view
    }

    private fun sendEmailRecoveryPassword(email: String){
        auth!!.sendPasswordResetEmail(email).addOnCompleteListener { task ->
            if (task.isSuccessful)
                Toast.makeText(context, "Check email to reset your password!", Toast.LENGTH_LONG).show()
            else
                Toast.makeText(context, "Fail to send reset password email!", Toast.LENGTH_LONG).show()
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

    override fun onDetach() {
        super.onDetach()
        statusListener = null
    }

}
