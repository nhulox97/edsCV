package com.nhulox.edscv.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException

import com.nhulox.edscv.R
import com.nhulox.edscv.model.User
import java.lang.ClassCastException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 */
class Profile : Fragment() {

    private lateinit var displayNameTV: TextView
    private lateinit var emailTV: TextView
    private lateinit var btnUpdate: Button

    private var editListener: EditListener? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        displayNameTV = view.findViewById(R.id.txtName)
        emailTV = view.findViewById(R.id.txtEmail)
        btnUpdate = view.findViewById(R.id.btnEdit)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        btnUpdate.setOnClickListener { editListener!!.exchangeUserFragment("edit") }

        return view
    }

    override fun onStart() {
        super.onStart()
        getUserData()
    }

    private fun getUserData(){
        val currentUser = auth.currentUser
        //displayNameTV.text = currentUser!!.displayName
        emailTV.text = currentUser!!.email
        var user: User?
        val reference = firestore.collection("users").
            document("${currentUser.uid}")
        try {
            reference.get().addOnSuccessListener { documentSnapshot ->
                user = documentSnapshot.toObject(User::class.java)
                displayNameTV.text = user!!.displayName
            }

        }
        catch (e: FirebaseFirestoreException){
            Log.d("Error Actual", "Error: $e")
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try {
            editListener = context as EditListener
        }catch (e: ClassCastException){
            throw java.lang.ClassCastException("$context you must implement the interface")
        }

    }

    override fun onDetach() {
        super.onDetach()
        editListener = null
    }

    override fun onResume() {
        super.onResume()
        getUserData()
    }

}
