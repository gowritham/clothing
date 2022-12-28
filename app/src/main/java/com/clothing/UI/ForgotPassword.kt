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
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.clothing.R



class ForgotPassword : Fragment() {
    lateinit var userEmail: EditText
    lateinit var submitBtn:Button
    lateinit var forgotHelperText : TextView


    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view=inflater.inflate(R.layout.fragment_forgot_password, container, false)
        userEmail=view.findViewById(R.id.forgotEmail)
        submitBtn=view.findViewById(R.id.submitBtn)
        forgotHelperText=view.findViewById(R.id.forgotHelperText)
        forgotHelperText.isVisible=false
        userEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches())
                    forgotHelperText.isVisible=false

                else{
                    forgotHelperText.isVisible=true
                    forgotHelperText.text="Invalid Email"
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })
       submitBtn.setOnClickListener {
           if (userEmail.text.toString().isEmpty()){
               forgotHelperText.isVisible=true
               forgotHelperText.text = "Required"

           } else {
               if (Patterns.EMAIL_ADDRESS.matcher(userEmail.text.toString()).matches()) {
                   Toast.makeText(activity, "Success", Toast.LENGTH_LONG).show()
               } else {
                   Toast.makeText(activity, "Failure", Toast.LENGTH_LONG).show()
               }
           }
        }

      return view
    }
}