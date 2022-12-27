package com.clothing.UI

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.clothing.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginFragment : Fragment() {
    private lateinit var login_emailContainer:TextInputLayout
    private lateinit var login_email:TextInputEditText
    private lateinit var login_passwordContainer:TextInputLayout
    private lateinit var login_password:TextInputEditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val signUp = view.findViewById<TextView>(R.id.tv_login_signup)
        val forgotPassword=view.findViewById<TextView>(R.id.et_forget)
        val log_in_button=view.findViewById<AppCompatButton>(R.id.login_button)
        login_email=view.findViewById(R.id.emailEditText)
        login_emailContainer=view.findViewById(R.id.emailContainer)
        login_passwordContainer=view.findViewById(R.id.passwordlContainer)
        login_password=view.findViewById(R.id.passwordEditText)
        //binding= FragmentLoginBinding.inflate(inflater,container,false)
        signUp.setOnClickListener {

            findNavController().navigate(R.id.action_loginFragment_to_accountCreationFragment)
        }
        log_in_button.setOnClickListener {
            if (login_email.text.toString().isEmpty() && login_password.text.toString().isEmpty()){
                login_emailContainer.helperText = "Enter Email"
                login_passwordContainer.helperText = "Enter Password"
                Toast.makeText(activity, "Please enter valid email and password", Toast.LENGTH_LONG)
                    .show()
            } else {
                if (validEmail() && validPassword()) {
                    //Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    //binding.passwordContainer.helperText="invalid password"
                    Toast.makeText(activity, "Failure", Toast.LENGTH_LONG).show()
                }
                //findNavController().navigate(R.id.action_loginpage2_to_welcome_screen)
            }

        }
        forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPassword)
        }

        return view
    }
    fun validEmail(): Boolean {
        if(!Patterns.EMAIL_ADDRESS.matcher(login_email.text.toString()).matches()){
            login_emailContainer.helperText="Invalid"
            return false
        } else{
            login_emailContainer.helperText=null
            return true
        }

    }
    fun validPassword(): Boolean {
        if(login_password.text.toString().length<8){
            login_passwordContainer.helperText= "8 characters"
            return false
        } else {
            login_passwordContainer.helperText=null
            return true
        }
    }
}