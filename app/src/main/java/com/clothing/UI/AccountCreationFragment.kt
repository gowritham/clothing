package com.clothing.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController

import com.clothing.R



class AccountCreationFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account_creation, container, false)

        val userName = view.findViewById<EditText>(R.id.editTextTextPersonName)
        val mailAddress = view.findViewById<EditText>(R.id.editTextTextEmailAddress)
        val password = view.findViewById<EditText>(R.id.editTextTextPassword)
        val button = view.findViewById<Button>(R.id.button)
        val loginText = view.findViewById<TextView>(R.id.login_register_text)

        //val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\.+[a-z]+"
        //val passwordPattern="\"^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\\\S+\$).{4,}\$\""

        button.setOnClickListener{
            if(userName.text.toString().isNotEmpty() && mailAddress.text.toString().isNotEmpty() && password.text.toString()
                    .isNotEmpty()){
                val k1 = Toast.makeText(
                    activity,
                    "Success",
                    Toast.LENGTH_LONG
                )
                k1.show()


            }else {

                val k = Toast.makeText(
                    activity,
                    "Enter the input fields",
                    Toast.LENGTH_LONG
                )
                k.show()
            }
        }
        loginText.setOnClickListener {
            findNavController().navigate(R.id.action_accountCreationFragment_to_loginFragment)
        }

        return view

    }

}