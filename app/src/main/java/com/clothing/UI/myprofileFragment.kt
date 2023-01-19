package com.clothing.UI

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.findFragment
import com.clothing.R


class myprofileFragment : Fragment() {
    var shared : String = "sharedPreference"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_myprofile, container, false)
        val appContext = requireContext().applicationContext
        val prefs = appContext.getSharedPreferences(shared, Context.MODE_PRIVATE)
        val name = prefs.getString("USERNAME",null)
        if (name != null) {
            Log.d("nnnn",name)
        }
        val profileName = view.findViewById<TextView>(R.id.profileUserName)
        profileName.text = name
        return view
    }
}