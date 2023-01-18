package com.clothing.UI

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.navigation.fragment.findNavController
import com.clothing.R

class SplashFragment : Fragment() {
    var shared : String = "sharedPreference"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        Handler(Looper.myLooper()!!).postDelayed({
            val appContext = requireContext().applicationContext
            val prefs = appContext.getSharedPreferences(shared, Context.MODE_PRIVATE)
            val check = prefs.getBoolean("islogin",false)
            if(check){
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
            else{
                findNavController().navigate(R.id.action_splashFragment_to_onboarding1Fragment)
            }

        },3000)
        return view
    }


}