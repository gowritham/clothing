package com.example.onboardingscreens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.clothing.R


class Onboarding2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_onboarding2, container, false)
        val button = view.findViewById<Button>(R.id.next2)
        val skip = view.findViewById<TextView>(R.id.skip2)
        button.setOnClickListener {
            findNavController().navigate(R.id.action_onboarding2Fragment_to_onboarding3Fragment)
        }
        skip.setOnClickListener{
            findNavController().navigate(R.id.action_onboarding2Fragment_to_loginFragment)
        }
        return view
    }

}