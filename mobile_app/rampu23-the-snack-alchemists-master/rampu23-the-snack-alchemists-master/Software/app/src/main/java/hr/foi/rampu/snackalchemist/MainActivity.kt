package hr.foi.rampu.snackalchemist


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.snackalchemist.adapters.MainAdapter
import hr.foi.rampu.snackalchemist.fragments.Articles
import hr.foi.rampu.snackalchemist.fragments.Cart
import hr.foi.rampu.snackalchemist.fragments.Catalog
import hr.foi.rampu.snackalchemist.fragments.Homepage
import hr.foi.rampu.snackalchemist.fragments.Orders


class MainActivity : AppCompatActivity() {
    lateinit var BottomNav: TabLayout
    lateinit var ViewPagerMain: ViewPager2
    private lateinit var ProfileMenuButton : ImageButton
    private lateinit var drawerLayout : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hideSystemUI()
        
        BottomNav = findViewById(R.id.navBar)
        ViewPagerMain = findViewById(R.id.viewPager2main)

        val imageButtonClick = findViewById<ImageButton>(R.id.openPromoCodeButton)
        imageButtonClick.setOnClickListener {
            val intent = Intent(this, PromoCodeActivity::class.java)
            startActivity(intent)
        }

        val mainPagerAdapter = MainAdapter(supportFragmentManager, lifecycle)
        mainPagerAdapter.addFragment(
            MainAdapter.FragmentItem(
                R.string.Catalog,
                R.drawable.ic_offers,
                Catalog::class
            )
        )
        mainPagerAdapter.addFragment(
            MainAdapter.FragmentItem(
                R.string.Articles,
                R.drawable.ic_articles,
                Articles::class
            )
        )
        mainPagerAdapter.addFragment(
            MainAdapter.FragmentItem(
                R.string.Homepage,
                R.drawable.ic_home,
                Homepage::class
            )
        )
        mainPagerAdapter.addFragment(
            MainAdapter.FragmentItem(
                R.string.Orders,
                R.drawable.ic_lastorders,
                Orders::class
            )
        )
        mainPagerAdapter.addFragment(
            MainAdapter.FragmentItem(
                R.string.Cart,
                R.drawable.ic_shopping_cart,
                Cart::class
            )
        )

        ViewPagerMain.adapter = mainPagerAdapter
        TabLayoutMediator(BottomNav, ViewPagerMain) { tab, position ->
            tab.setText(mainPagerAdapter.fragmentItems[position].titleRes)
            tab.setIcon(mainPagerAdapter.fragmentItems[position].iconRes)
        }.attach()

        drawerLayout=findViewById<DrawerLayout>(R.id.drawer_layout)
        ProfileMenuButton=findViewById<ImageButton>(R.id.ibtnProfileMenu)
        ProfileMenuButton.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
        val navView = findViewById<NavigationView>(R.id.nav_view)
        val intent= Intent(this,LoginActivity::class.java)
        navView.setNavigationItemSelectedListener {menuitem ->
            when(menuitem.itemId){
                R.id.Logout -> startActivity(intent)
                else -> Toast.makeText(this,"Greška",Toast.LENGTH_LONG).show()
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