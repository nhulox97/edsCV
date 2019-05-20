package com.nhulox.edscv

import android.app.FragmentManager
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.nhulox.edscv.controller.FireAuth
import com.nhulox.edscv.view.ForgotPassword
import com.nhulox.edscv.view.LogIn
import com.nhulox.edscv.view.SignIn
import com.nhulox.edscv.view.UsersFragmentListener

class MainActivity : AppCompatActivity(), UsersFragmentListener{

    private lateinit var signIn: SignIn
    private lateinit var logIn: LogIn
    private lateinit var forgotPassword: ForgotPassword
    private lateinit var fireAuth: FireAuth

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signIn = SignIn()
        logIn = LogIn()
        forgotPassword = ForgotPassword()

        //auth = FirebaseAuth.getInstance()

        fireAuth = FireAuth(applicationContext)
        userIsLogged()


        replaceFragment(logIn)

    }



    override fun getActualFragment(option: String) {
        super.getActualFragment(option)
        setViewFragment(option)
    }

    private fun setViewFragment(option: String){
        when (option) {
            "LogIn" -> replaceFragment(logIn)
            "SignIn" -> replaceFragment(signIn)
            "ForgotPassword" -> replaceFragment(forgotPassword)
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun userIsLogged(){
        if (fireAuth.verifyCurrentUser()){
            val intent = Intent(this, NavBarMainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        userIsLogged()
    }

    override fun onRestart() {
        super.onRestart()
        userIsLogged()
    }

}
