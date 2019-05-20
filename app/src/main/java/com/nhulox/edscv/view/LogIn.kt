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
import com.nhulox.edscv.NavBarMainActivity

import com.nhulox.edscv.R
import com.nhulox.edscv.controller.FireAuth
import java.lang.NullPointerException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class LogIn : Fragment(), UsersFragmentListener {

    private var signInTV: TextView? = null
    private var email: EditText? = null
    private var password: EditText? = null
    private var btnLogIn: Button? = null
    private var forgotTV: TextView? = null

    private var emailTx: String = ""
    private var passwordTx: String = ""



    private lateinit var auth: FirebaseAuth

    private var statusListener: UsersFragmentListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_log_in, container, false)
        signInTV = view.findViewById(R.id.signInFragment)
        forgotTV = view.findViewById(R.id.forgotPassword)
        email = view.findViewById(R.id.emailL)
        password = view.findViewById(R.id.passwordL)
        btnLogIn = view.findViewById(R.id.btnLogIn)

        auth = FirebaseAuth.getInstance()

        btnLogIn!!.setOnClickListener { logInUser() }

        signInTV!!.setOnClickListener{ statusListener!!.getActualFragment("SignIn") }
        forgotTV!!.setOnClickListener { statusListener!!.getActualFragment("ForgotPassword") }

        //userIsLogged()

        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try{
            statusListener = context as UsersFragmentListener
        }catch (e: ClassCastException){
            throw java.lang.ClassCastException("$context you must implement the interface")
        }
    }

    private fun logInUser(){
        val emailT = email!!.text.toString()
        val passwordT = password!!.text.toString()

       if (!TextUtils.isEmpty(emailT) && !TextUtils.isEmpty(passwordT)){
           auth.signInWithEmailAndPassword(emailT, passwordT)
               .addOnCompleteListener { task ->
                   if (task.isSuccessful) {
                       Log.d("${R.string.AuthTAG}", "logInWithEmail:success")

                       Toast.makeText(context, "${context!!.resources.getString(R.string.authSuccess)}",
                           Toast.LENGTH_SHORT).show()

                       val intent = Intent(context, NavBarMainActivity::class.java)
                       startActivity(intent)
                   } else {
                       Toast.makeText(context, "${context!!.resources.getString(R.string.AuthError)}",
                           Toast.LENGTH_SHORT).show()
                   }
               }
       }
       else
           Toast.makeText(context, "${context!!.resources.getString(R.string.emptyFields)}", Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        statusListener = null
    }

    override fun onResume() {
        super.onResume()
        if ( emailTx != null && passwordTx != null ){
            email!!.setText(emailTx)
            password!!.setText(passwordTx)
        }
    }

    override fun onStop() {
        super.onStop()
        emailTx = email!!.text.toString()
        passwordTx = password!!.text.toString()
    }
}
