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

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val signUp = view.findViewById<TextView>(R.id.signUp)

        val log_in_button=view.findViewById<Button>(R.id.loginBtn)
        signUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_accountCreationFragment)
        }
        log_in_button.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_product_details)
        }
        return view
    }
}