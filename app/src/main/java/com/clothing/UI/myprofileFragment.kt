package com.clothing.UI

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.clothing.R
import com.clothing.UI.retrofit.AllUsersItem
import com.clothing.UI.retrofit.ProductsApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class myprofileFragment : Fragment() {
    var shared : String = "sharedPreference"
    var emailList : ArrayList<String>? = arrayListOf()
    var nameList : ArrayList<String>? = arrayListOf()
    var zipCodeList : ArrayList<String>? = arrayListOf()
    var numberList : ArrayList<String>? = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_myprofile, container, false)
        getMyDate()
        return view
    }

    private fun getMyDate() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fakestoreapi.com")
            .build()
            .create(ProductsApi::class.java)

        val  retrofitData = retrofitBuilder.getDate()
        retrofitData.enqueue(object : Callback<List<AllUsersItem>?> {
            override fun onResponse(call: Call<List<AllUsersItem>?>, response: Response<List<AllUsersItem>?>) {
                val responseBody = response.body()!!
                val appContext = requireContext().applicationContext
                val prefs = appContext.getSharedPreferences(shared, Context.MODE_PRIVATE)
                val name = prefs.getString("USERNAME","")
                Log.d("nnnnn",responseBody.toString())
                for (i in responseBody.indices){
                    emailList?.add(responseBody.get(i).email)
                    nameList?.add(responseBody.get(i).username)
                    zipCodeList?.add(responseBody[i].address.zipcode)
                    numberList?.add(responseBody[i].phone)
                    // Log.d("sss", responseBody.get(i).email.toString())
                }
                if(nameList?.contains(name) == true){
                    val k = nameList!!.indexOf(name)
                    val n = emailList?.get(k)
                    val z = zipCodeList?.get(k)
                    val p = numberList?.get(k)
                    Log.d("SSSS","$z")
                    Log.d("SSSS","$p")
                    val username: TextView? = view?.findViewById(R.id.profileName)
                    val email:TextView? = view?.findViewById(R.id.profileEmail)
                    val name1 : TextView? = view?.findViewById(R.id.textView15)
                    val email1 : TextView? = view?.findViewById(R.id.textView18)
                    val zipCode : TextView? = view?.findViewById(R.id.textView20)
                    val number :TextView? = view?.findViewById(R.id.textView21)
                    username?.text=name
                    email?.text=n
                    name1?.text = name
                    email1?.text = n
                    zipCode?.text = z
                    number?.text = p
                }
            }
            override fun onFailure(call: Call<List<AllUsersItem>?>, t: Throwable) {
                Log.d("mainActivity","onFailure:"+t.message)
            }
        })
    }
}