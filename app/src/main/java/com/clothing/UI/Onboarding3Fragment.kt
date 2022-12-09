package com.clothing.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.clothing.R

class Onboarding3Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_onboarding3, container, false)
        val button = view.findViewById<Button>(R.id.next3)
        val skip = view.findViewById<TextView>(R.id.skip3)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_onboarding3Fragment_to_loginFragment)
        }
        skip.setOnClickListener{
            findNavController().navigate(R.id.action_onboarding3Fragment_to_loginFragment2)
        }
        return view
    }

}