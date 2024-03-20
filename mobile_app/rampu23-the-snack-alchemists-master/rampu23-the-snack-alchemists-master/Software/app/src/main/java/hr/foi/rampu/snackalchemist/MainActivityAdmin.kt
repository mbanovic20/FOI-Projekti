package hr.foi.rampu.snackalchemist

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController

import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.snackalchemist.adapters.MainAdapter
import hr.foi.rampu.snackalchemist.fragments.Articles

import hr.foi.rampu.snackalchemist.fragments.CatalogAdmin
import hr.foi.rampu.snackalchemist.fragments.Mail

class MainActivityAdmin : AppCompatActivity() {
    lateinit var BottomNavAdmin: TabLayout
    lateinit var ViewPagerMainAdmin: ViewPager2
    private lateinit var ProfileMenuButtonAdmin : ImageButton
    private lateinit var drawerLayoutAdmin : DrawerLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)

        hideSystemUI()

        val email = intent.getStringExtra("email")

        BottomNavAdmin = findViewById(R.id.navBarAdmin)
        ViewPagerMainAdmin = findViewById(R.id.viewPager2mainAdmin)
        val mainPagerAdapterAdmin = MainAdapter(supportFragmentManager, lifecycle)
        mainPagerAdapterAdmin.addFragment(
            MainAdapter.FragmentItem(
                R.string.Catalog,
                R.drawable.ic_offers,
                CatalogAdmin::class
            )
        )
        mainPagerAdapterAdmin.addFragment(
            MainAdapter.FragmentItem(
                R.string.Articles,
                R.drawable.ic_articles,
                Articles::class
            )
        )

        mainPagerAdapterAdmin.addFragment(
            MainAdapter.FragmentItem(
                R.string.Email,
                R.drawable.ic_dialog_email,
                Mail::class
            )
        )

        ViewPagerMainAdmin.adapter = mainPagerAdapterAdmin
        TabLayoutMediator(BottomNavAdmin, ViewPagerMainAdmin) { tab, position ->
            tab.setText(mainPagerAdapterAdmin.fragmentItems[position].titleRes)
            tab.setIcon(mainPagerAdapterAdmin.fragmentItems[position].iconRes)
        }.attach()

        drawerLayoutAdmin=findViewById<DrawerLayout>(R.id.drawer_layout_admin)
        ProfileMenuButtonAdmin=findViewById<ImageButton>(R.id.ProfileMenuAdmin)
        ProfileMenuButtonAdmin.setOnClickListener {
            drawerLayoutAdmin.openDrawer(GravityCompat.START)
        }
        val navView = findViewById<NavigationView>(R.id.nav_view_admin)
        val intent= Intent(this, LoginActivity::class.java)
        navView.setNavigationItemSelectedListener {menuitem ->
            when(menuitem.itemId){
                R.id.Logout -> startActivity(intent)
                else -> Toast.makeText(this,"Greška", Toast.LENGTH_LONG).show()
            }
            return@setNavigationItemSelectedListener true
        }
    }

    private fun hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.let {
                it.hide(WindowInsets.Type.statusBars())
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
        }
    }
}