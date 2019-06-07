package com.nhulox.edscv.view


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
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
 *
 */
class EditProfileFragment : Fragment() {

    private var editListener: EditListener? = null

    private lateinit var btnUpdate: Button
    private lateinit var userET: EditText

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragmentw
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        btnUpdate = view.findViewById(R.id.btnUpdate)
        userET = view.findViewById(R.id.userU)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        getUserData()

        btnUpdate.setOnClickListener { setUserData() }


        return view
    }

    private fun getUserData(){
        val currentUser = auth.currentUser
        var user: User?

        val reference = firestore.collection("users").
            document("${currentUser!!.uid}")
        try {
            reference.get().addOnSuccessListener { documentSnapshot ->
                user = documentSnapshot.toObject(User::class.java)
                userET.setText(user!!.displayName)
            }

        }
        catch (e: FirebaseFirestoreException){
            Log.d("Error Actual", "Error: $e")
        }

    }

    private fun setUserData(){
        val displayName = userET.text.toString()

        val currentUser = auth.currentUser
        try {
            firestore.collection("users").document(currentUser!!.uid)
                .update("displayName",displayName)
                .addOnSuccessListener{
                    Toast.makeText(context, "Your data was successfully updated", Toast.LENGTH_LONG).show()
                    editListener!!.exchangeUserFragment("profile")
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error updating your data", Toast.LENGTH_LONG).show()
                }

        }catch (e: FirebaseFirestoreException){
            e.printStackTrace()
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

}
