package com.clothing.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.clothing.R


class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_settings, container, false)
        val faqNav = view.findViewById<ImageView>(R.id.faq_nav)
        val psNav = view.findViewById<ImageView>(R.id.ps_nav)
        faqNav.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.FAQFragment)
        }
        psNav.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.privacyFragment)
        }
        return view
    }

}