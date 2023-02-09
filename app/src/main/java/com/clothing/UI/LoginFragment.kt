package com.clothing.UI

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.clothing.R
import com.clothing.UI.retrofit.DataModal
import com.clothing.UI.retrofit.ProductsApi
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {
    @SuppressLint("SetTextI18n")
    private lateinit var emailHelperText: TextView
    private lateinit var passwordHelperText: TextView
    var shared : String = "sharedPreference"
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val etUsername = view.findViewById<EditText>(R.id.etUsername)
        val etPassword = view.findViewById<EditText>(R.id.et_password)
        emailHelperText = view.findViewById(R.id.emailHelperText)
        passwordHelperText = view.findViewById(R.id.passwordHelperText)
        val signUp = view.findViewById<TextView>(R.id.tv_login_signup)
        val forgotPassword = view.findViewById<TextView>(R.id.et_forget)
        val log_in_button = view.findViewById<AppCompatButton>(R.id.login_button)
        val google = view.findViewById<ImageView>(R.id.login_google_icon)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = activity?.let { GoogleSignIn.getClient(it,gso) }!!
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if(account != null){
            findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        }
        google.setOnClickListener {
            gotoSignIn()
        }
        emailHelperText.isVisible = false
        passwordHelperText.isVisible = false

        etUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (etUsername.text.toString().isNotEmpty()) {
                    emailHelperText.isVisible = false
                } else {
                    emailHelperText.isVisible = true
                    emailHelperText.text = "Invalid UserName"
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pwdText = etPassword.text.toString()
                if (pwdText.isNotEmpty()) {
                    passwordHelperText.isVisible = false
                } else {
                    passwordHelperText.isVisible = true
                    passwordHelperText.text = "Invalid Password"
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        signUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_accountCreationFragment)
        }
        log_in_button.setOnClickListener {
            if (etUsername.text.toString().isEmpty() && etPassword.text.toString().isEmpty()) {
                emailHelperText.isVisible = true
                emailHelperText.text = "UserName Required"
                passwordHelperText.isVisible = true
                passwordHelperText.text = "Password Required"
            } else {
                postData(etUsername.text.toString(), etPassword.text.toString())

            }

        }
        forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPassword)
        }

        return view
    }

    private fun gotoSignIn() {
        val signInFragment = gsc.signInIntent
        startActivityForResult(signInFragment,1000)
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000){
            val task: Task<GoogleSignInAccount> = GoogleSignIn
                .getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

            }
            catch(e:java.lang.Exception){
                Toast.makeText(activity,e.message,Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun postData(username: String, password: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://fakestoreapi.com/auth/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val retrofitApi = retrofit.create(ProductsApi::class.java)

        val dataModal = DataModal(username, password)

        val call: Call<DataModal?>? = retrofitApi.postData(dataModal)

        call!!.enqueue(object : Callback<DataModal?> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<DataModal?>, response: Response<DataModal?>) {
                if (response.code() == 200) {
                    val appContext = requireContext().applicationContext
                    val prefs = appContext.getSharedPreferences(shared,Context.MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putBoolean("islogin",true)
                    editor.putString("USERNAME",username)
                    editor.apply()
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                    //Toast.makeText(activity, "Data added to API", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(activity, "username or password is wrong", Toast.LENGTH_SHORT)
                        .show()
                    emailHelperText.isVisible = true
                    emailHelperText.text = "Invalid username"
                    passwordHelperText.isVisible = true
                    passwordHelperText.text = "Invalid Password"
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onFailure(call: Call<DataModal?>, t: Throwable) {
                // setting text to our text view when
                // we get error response from API.
                //response.text = "Error found is : "+t.message

            }

        })
    }
}