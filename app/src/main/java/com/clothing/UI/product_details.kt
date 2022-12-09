package com.clothing.UI

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.clothing.R


class product_details : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_product_details2, container, false)
        val addToCart= view.findViewById<Button>(R.id.pd_button)
        addToCart.setOnClickListener {
            findNavController().navigate(R.id.action_product_details_to_settingsFragment)
        }

        return view

    }




 }