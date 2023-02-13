package com.clothing.UI

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController

import com.clothing.R
import com.clothing.UI.retrofit.AllUsersItem
import com.clothing.UI.retrofit.ProductsApi
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class AccountCreationFragment : Fragment() {

    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    var callBackManager: CallbackManager? = null
    var facebookLoginButton: LoginButton? = null
    var facebookIcon : ImageView? = null
    var emailList : ArrayList<String> = arrayListOf()
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account_creation, container, false)
        callBackManager = CallbackManager.Factory.create()
        facebookLoginButton = view.findViewById(R.id.login_button_facebook)
        facebookIcon = view.findViewById(R.id.register_facebook_icon)

        facebookIcon?.setOnClickListener {
            facebookLoginButton?.performClick()
            facebookInit()
        }

        val userName = view.findViewById<EditText>(R.id.editTextTextPersonName)
        val mailAddress = view.findViewById<EditText>(R.id.editTextTextEmailAddress)
        val password = view.findViewById<EditText>(R.id.editTextTextPassword)
        val button = view.findViewById<Button>(R.id.button)
        val chechbox= view.findViewById<CheckBox>(R.id.checkBox)
        val loginText = view.findViewById<TextView>(R.id.login_register_text)
        val nameHelper = view.findViewById<TextView>(R.id.nameHelper)
        val emailHelper = view.findViewById<TextView>(R.id.emailHelper)
        val passwordHelper = view.findViewById<TextView>(R.id.passwordHelper)
        val google = view.findViewById<ImageView>(R.id.register_google_icon)
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = activity?.let { GoogleSignIn.getClient(it,gso) }!!
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireActivity())
        if(account != null){
            goToHome()
        }
        google.setOnClickListener {
            gotoSignIn()
        }
        nameHelper.isVisible=false
        emailHelper.isVisible=false
        passwordHelper.isVisible=false
        userName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            @SuppressLint("SetTextI18n")
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
            @SuppressLint("SetTextI18n")
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
            @SuppressLint("SetTextI18n")
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pwdText=password.text.toString()
                if(pwdText.length<6) {
                    passwordHelper.isVisible = true
                    passwordHelper.text = "Minimum 6"
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
                nameHelper.text="Name Required"
                emailHelper.isVisible=true
                emailHelper.text = "Email Required"
                passwordHelper.isVisible=true
                passwordHelper.text="Password Required"
            } else {
                if (Patterns.EMAIL_ADDRESS.matcher(mailAddress.text.toString()).matches() && userName.text.toString().isNotEmpty() && password.text.length>=6 && chechbox.isChecked) {
                        val retrofitBuilder = Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl("https://fakestoreapi.com")
                        .build()
                        .create(ProductsApi::class.java)

                    val  retrofitData = retrofitBuilder.getDate()
                    retrofitData.enqueue(object : Callback<List<AllUsersItem>?> {
                        override fun onResponse(call: Call<List<AllUsersItem>?>, response: Response<List<AllUsersItem>?>) {
                            for(i in response.body()?.indices!!){
                                response.body()?.get(i)?.let { it1 -> emailList.add(it1.email) }
                            }
                            if(emailList.contains(mailAddress.text.toString())){
                                Snackbar.make(it,"Already Existed",Snackbar.LENGTH_LONG).show()
                            }
                            else{
                                Snackbar.make(it,"Registered Successfully",Snackbar.LENGTH_LONG).show()
                            }


                        }
                        override fun onFailure(call: Call<List<AllUsersItem>?>, t: Throwable) {
                            Log.d("mainActivity","onFailure:"+t.message)
                        }
                    })




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

    private fun gotoSignIn() {
        val signInFragment = gsc.signInIntent
        startActivityForResult(signInFragment,1000)
    }


    // Facebook icon connection work stop
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callBackManager?.onActivityResult(requestCode,resultCode,data)
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000){
            val task: Task<GoogleSignInAccount> = GoogleSignIn
                .getSignedInAccountFromIntent(data)
            try {

                task.getResult(ApiException::class.java)
                goToHome()

            }
            catch(e:java.lang.Exception){
                Toast.makeText(activity,e.message,Toast.LENGTH_SHORT).show()

            }
        }
    }
    private fun goToHome() {
        findNavController().navigate(R.id.action_accountCreationFragment_to_homeFragment)
    }
    private fun facebookInit(){
        facebookLoginButton?.setPermissions("email","public_profile")
        callBackManager?.let {
            facebookLoginButton?.registerCallback(it, object: FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    val request = GraphRequest.newMeRequest(result.accessToken){ `object`, response ->
                        if (`object` != null) {
                            retrieveFacebookData(`object`)
                        }

                    }
                    val parm = Bundle()
                    parm.putString("fields" , "id , name , email , gender , birthday")
                    request.parameters = parm
                    request.executeAsync()
                }

                override fun onCancel() {
                    Toast.makeText(requireContext(),"Login Canceled", Toast.LENGTH_SHORT).show()

                }

                override fun onError(error: FacebookException) {
                    error.printStackTrace()
                }
            })
        }
    }

    private fun retrieveFacebookData(jsonObject: JSONObject){
        try {
            //var pictureUrl = "http:graph.facebook.com/${jsonObject.getString("id")}/picture?type=large"
            val name = jsonObject.getString("name")
            //var email = jsonObject.getString("email")

            /*val intent = Intent(this, FacebookDetails::class.java)
            intent.putExtra("name",name)
            intent.putExtra("email",email)
            intent.putExtra("profile" ,pictureUrl)
            startActivity(intent)*/

            Log.d("FACEBOOK LOGIN","name: ${name}")

        }catch (e: JSONException){
            e.printStackTrace()

        }

    }



}