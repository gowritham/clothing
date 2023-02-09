package com.clothing.UI

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.core.graphics.green
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.clothing.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.color.MaterialColors.getColor
import com.google.android.material.navigation.NavigationView


class HomeFragment : Fragment() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var appBar : MaterialToolbar
    var shared : String = "sharedPreference"
    private lateinit var gso: GoogleSignInOptions
    private lateinit var gsc: GoogleSignInClient
    @SuppressLint("ApplySharedPref")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_home, container, false)

        // Inflate the layout for this fragment

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        gsc = activity?.let { GoogleSignIn.getClient(it,gso) }!!
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireActivity())


        val appContext = requireContext().applicationContext
        val prefs = appContext.getSharedPreferences(shared, Context.MODE_PRIVATE)
        drawerLayout = view.findViewById(R.id.drawerlayout)
        appBar = view.findViewById(R.id.appBar)
        val navigationView = view.findViewById<NavigationView>(R.id.nav_view)
        val bottomNavigationView=view.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        replaceFragment(ProductListingFragment())
        appBar.setNavigationOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        val header=navigationView.getHeaderView(0)
        val textH=header.findViewById<TextView>(R.id.headerName)
        textH.text=prefs.getString("USERNAME","")
        if(account != null){
            textH.text=account.displayName
        }
        val counter:Int=prefs.getInt("count",0)
        bottomNavigationView.getOrCreateBadge(R.id.cart).number = counter
        bottomNavigationView.getOrCreateBadge(R.id.cart).backgroundColor = Color.parseColor("#F67952")
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
                    appBar.setNavigationIcon(R.drawable.menu)
                    appBar.setTitle(R.string.app_name)
                    bottomNavigationView.isVisible = true

                }
                R.id.myorder -> {
                    replaceFragment(MyOrdersFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    appBar.setNavigationIcon(R.drawable.ic_back_icon)
                    appBar.setTitle(R.string.side_myorder)
                    bottomNavigationView.isVisible = false
                }
                R.id.Aboutus -> {
                    replaceFragment(AboutUsFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    appBar.setNavigationIcon(R.drawable.ic_back_icon)
                    appBar.setTitle(R.string.side_aboutus)
                    bottomNavigationView.isVisible = false
                }
                R.id.checkOut -> {
                    replaceFragment(CheckOutFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    appBar.setNavigationIcon(R.drawable.ic_back_icon)
                    appBar.setTitle(R.string.side_checkout)
                    bottomNavigationView.isVisible = false
                }
                R.id.logout -> {
                    val appContext1 = requireContext().applicationContext
                    val pref = appContext1.getSharedPreferences(shared, Context.MODE_PRIVATE)
                    val editor = pref.edit()
                    editor.putBoolean("islogin",false)
                    editor.apply()
                    editor.clear().commit()
                    findNavController().navigate(R.id.loginFragment)
                    gsc.signOut().addOnSuccessListener {
                        findNavController().navigate(R.id.loginFragment)
                    }
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