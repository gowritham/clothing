package com.clothing.UI

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import com.clothing.UI.AboutUsFragment
import com.clothing.UI.MyorderFragment
import com.clothing.R
import com.clothing.UI.WalletsFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class HomeFragment : Fragment() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var appBar : MaterialToolbar
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
                    appBar.setTitle(R.string.side_settings)
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
                    replaceFragment(FavouriteFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    appBar.setNavigationIcon(R.drawable.ic_back_icon)
                    appBar.setTitle(R.string.side_favorite)
                }
                R.id.wallest -> {
                    replaceFragment(WalletsFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    appBar.setNavigationIcon(R.drawable.ic_back_icon)
                    appBar.setTitle(R.string.side_wallest)
                }
                R.id.myorder -> {
                    replaceFragment(MyorderFragment())
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
                R.id.setting -> {
                    replaceFragment(SettingsFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                    appBar.setNavigationIcon(R.drawable.ic_back_icon)
                    appBar.setTitle(R.string.side_settings)
                }
                R.id.logout -> findNavController().navigate(R.id.loginFragment)
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