package com.clothing.UI

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.clothing.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPassword = view.findViewById<EditText>(R.id.et_password)
        val emailHelperText = view.findViewById<TextView>(R.id.emailHelperText)
        val passwordHelperText = view.findViewById<TextView>(R.id.passwordHelperText)
        val signUp = view.findViewById<TextView>(R.id.tv_login_signup)
        val forgotPassword=view.findViewById<TextView>(R.id.et_forget)
        val log_in_button=view.findViewById<AppCompatButton>(R.id.login_button)
        emailHelperText.isVisible=false
        passwordHelperText.isVisible=false
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches())
                    emailHelperText.isVisible=false

                else{
                    emailHelperText.isVisible=true
                    emailHelperText.text="Invalid Email"
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
        etPassword.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pwdText=etPassword.text.toString()
                if(pwdText.length<8) {
                    passwordHelperText.isVisible = true
                    passwordHelperText.text = "Minimum 8"
                }
                else{
                    passwordHelperText.isVisible = false
                }
            }
            override fun afterTextChanged(s: Editable?) {

            }

        })
        signUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_accountCreationFragment)
        }
        log_in_button.setOnClickListener {
            if (etEmail.text.toString().isEmpty() && etPassword.text.toString().isEmpty()){
                emailHelperText.isVisible=true
                emailHelperText.text = "Email Required"
                passwordHelperText.isVisible=true
                passwordHelperText.text="Password Required"
            } else {
                if (Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches() && etPassword.text.length>=8) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    Toast.makeText(activity, "Failure", Toast.LENGTH_LONG).show()
                }
            }

        }
        forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPassword)
        }

        return view
    }
}