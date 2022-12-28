package com.clothing.UI

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
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
        val chechbox= view.findViewById<CheckBox>(R.id.checkBox)
        val loginText = view.findViewById<TextView>(R.id.login_register_text)
        val nameHelper = view.findViewById<TextView>(R.id.nameHelper)
        val emailHelper = view.findViewById<TextView>(R.id.emailHelper)
        val passwordHelper = view.findViewById<TextView>(R.id.passwordHelper)
        nameHelper.isVisible=false
        emailHelper.isVisible=false
        passwordHelper.isVisible=false
        userName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(userName.text.toString().isNotEmpty())
                    nameHelper.isVisible=false

                else{
                    nameHelper.isVisible=true
                    nameHelper.text="Enter Name"
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        mailAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(Patterns.EMAIL_ADDRESS.matcher(mailAddress.text.toString()).matches())
                    emailHelper.isVisible=false

                else{
                    emailHelper.isVisible=true
                    emailHelper.text="Invalid Email"
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        password.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pwdText=password.text.toString()
                if(pwdText.length<8) {
                    passwordHelper.isVisible = true
                    passwordHelper.text = "Minimum 8"
                }
                else{
                    passwordHelper.isVisible = false
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }

        })
        button.setOnClickListener{
            if (userName.text.toString().isEmpty() && mailAddress.text.toString().isEmpty() && password.text.toString().isEmpty()){
                nameHelper.isVisible=true
                nameHelper.text="Required"
                emailHelper.isVisible=true
                emailHelper.text = "Required"
                passwordHelper.isVisible=true
                passwordHelper.text="Required"
            } else {
                if (Patterns.EMAIL_ADDRESS.matcher(mailAddress.text.toString()).matches() && userName.text.toString().isNotEmpty() && password.text.length>=8 && chechbox.isChecked) {
                    Toast.makeText(activity, "Registered Successfully", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(activity, "Failure", Toast.LENGTH_LONG).show()
                }
            }

        }
        loginText.setOnClickListener {
            findNavController().navigate(R.id.action_accountCreationFragment_to_loginFragment)
        }

        return view

    }

}