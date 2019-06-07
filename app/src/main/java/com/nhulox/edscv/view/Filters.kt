package com.nhulox.edscv.view


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.CardView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nhulox.edscv.CannyActivity
import com.nhulox.edscv.DetectorActivity

import com.nhulox.edscv.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class Filters : Fragment() {

    private lateinit var cannyCard: CardView
    private lateinit var detectorCard: CardView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_filters, container, false)

        cannyCard = view.findViewById(R.id.cannyFilter)
        detectorCard = view.findViewById(R.id.detectorActivity)

        cannyCard.setOnClickListener { cannyActivity() }
        detectorCard.setOnClickListener { detectorActivity() }

        return view
    }

    private fun cannyActivity(){
        val intent = Intent(context, CannyActivity::class.java)
        startActivity(intent)
    }

    private fun detectorActivity(){
        val intent = Intent(context, DetectorActivity::class.java)
        startActivity(intent)
    }

}
