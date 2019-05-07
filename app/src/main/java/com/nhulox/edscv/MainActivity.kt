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
import com.nhulox.edscv.view.LogIn
import com.nhulox.edscv.view.SignIn
import com.nhulox.edscv.view.UsersFragmentListener

class MainActivity : AppCompatActivity(), UsersFragmentListener{

    private lateinit var signIn: Fragment
    private lateinit var logIn: LogIn
    private lateinit var fireAuth: FireAuth

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signIn = SignIn()
        logIn = LogIn()

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
        if (option == "LogIn")
            replaceFragment(logIn)
        else if (option == "SignIn")
            replaceFragment(signIn)
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


}
