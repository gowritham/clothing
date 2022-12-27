package com.clothing.UI

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.clothing.R

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class ForgotPassword : Fragment() {

   // private lateinit var binding:FragmentForgotPasswordBinding
    lateinit var  emailContainer:TextInputLayout
    lateinit var userEmail:TextInputEditText
    lateinit var submitBtn:Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_forgot_password, container, false)

        emailContainer=view.findViewById(R.id.emailContainer)
        userEmail=view.findViewById(R.id.userEmail)
        submitBtn=view.findViewById(R.id.submitBtn)

       submitBtn.setOnClickListener {
                  if(userEmail.text.toString().isEmpty()){
                      emailContainer.helperText="Enter email"
                  }else{


                      val email=userEmail.text.toString()
                      if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                          emailContainer.helperText = "Invalid Email"
                          Toast.makeText(activity, "Failure", Toast.LENGTH_LONG).show()
                      }else{
                          emailContainer.helperText=null
                          Toast.makeText(activity,"Correct", Toast.LENGTH_LONG).show()

                      }
                  }
        }

      return view
    }
}