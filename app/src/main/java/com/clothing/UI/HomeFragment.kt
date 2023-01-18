package com.clothing.UI

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.clothing.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class HomeFragment : Fragment() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var appBar : MaterialToolbar
    var shared : String = "sharedPreference"
    @SuppressLint("ApplySharedPref")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_home, container, false)

        // Inflate the layout for this fragment
        drawerLayout = view.findViewById(R.id.drawerlayout)
        appBar = view.findViewById(R.id.appBar)
        val navigationView = view.findViewById<NavigationView>(R.id.nav_view)
        val bottomNavigationView=view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        replaceFragment(ProductListingFragment())
        appBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> {
                    replaceFragment(ProductListingFragment())
                    appBar.setNavigationOnClickListener {
                        drawerLayout.openDrawer(GravityCompat.START)
                    }
                    appBar.setTitle(R.string.app_name)
                    appBar.setNavigationIcon(R.drawable.menu)
                }
                R.id.cart -> {
                    replaceFragment(MyCartFragment())
                    appBar.setTitle(R.string.card_heading)
                    appBar.setNavigationIcon(R.drawable.ic_back_icon)
                    appBar.setNavigationOnClickListener {
                        replaceFragment(HomeFragment())
                        appBar.setNavigationOnClickListener {
                            drawerLayout.openDrawer(GravityCompat.START)
                        }
                        appBar.setTitle(R.string.app_name)
                        appBar.setNavigationIcon(R.drawable.menu)
                    }
                }
                R.id.setting -> {
                    replaceFragment(SettingsFragment())
                    appBar.setTitle(R.string.bottom_settings)
                    appBar.setNavigationIcon(R.drawable.ic_back_icon)
                    appBar.setNavigationOnClickListener {
                        replaceFragment(HomeFragment())
                        appBar.setNavigationOnClickListener {
                            drawerLayout.openDrawer(GravityCompat.START)
                        }
                        appBar.setTitle(R.string.app_name)
                        appBar.setNavigationIcon(R.drawable.menu)
                    }
                }
                R.id.profile -> {
                    replaceFragment(myprofileFragment())
                    appBar.setTitle(R.string.profile)
                    appBar.setNavigationIcon(R.drawable.ic_back_icon)
                    appBar.setNavigationOnClickListener {
                        replaceFragment(HomeFragment())
                        appBar.setNavigationOnClickListener {
                            drawerLayout.openDrawer(GravityCompat.START)
                        }
                        appBar.setTitle(R.string.app_name)
                        appBar.setNavigationIcon(R.drawable.menu)
                    }
                }

            }
            true
        }
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.favaurite -> {
                    replaceFragment(ProductListingFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    //appBar.setNavigationIcon(R.drawable.ic_back_icon)
                    appBar.setTitle(R.string.app_name)
                }
                R.id.myorder -> {
                    replaceFragment(MyOrdersFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    appBar.setNavigationIcon(R.drawable.ic_back_icon)
                    appBar.setTitle(R.string.side_myorder)
                }
                R.id.Aboutus -> {
                    replaceFragment(AboutUsFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    appBar.setNavigationIcon(R.drawable.ic_back_icon)
                    appBar.setTitle(R.string.side_aboutus)
                }
                R.id.checkOut -> {
                    replaceFragment(CheckOutFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    appBar.setNavigationIcon(R.drawable.ic_back_icon)
                    appBar.setTitle(R.string.side_checkout)
                }
                R.id.logout -> {
                    val appContext = requireContext().applicationContext
                    val prefs = appContext.getSharedPreferences(shared, Context.MODE_PRIVATE)
                    val editor = prefs.edit()
                    editor.putBoolean("islogin",false)
                    editor.apply()
                    editor.clear().commit()
                    findNavController().navigate(R.id.loginFragment)
                    Toast.makeText(activity,"Logged out Successful",Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        return view
    }
    private fun replaceFragment(fragment : Fragment) {

        val fragmentManager= fragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.frame_layout,fragment)
        fragmentTransaction?.commit()
    }
}