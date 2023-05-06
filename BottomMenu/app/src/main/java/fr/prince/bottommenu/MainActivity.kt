package fr.prince.bottommenu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.prince.bottommenu.fragments.FavoritesFragment
import fr.prince.bottommenu.fragments.HomeFragment
import fr.prince.bottommenu.fragments.SearchFragment

class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.nav_view)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, HomeFragment())
            .commit()


        bottomNavigationView.setOnItemSelectedListener {
            var fragment: Fragment? = null
            when (it.itemId) {
                R.id.nav_home -> {
                    fragment = HomeFragment()
                }
                R.id.nav_favorites -> {
                    fragment = FavoritesFragment()
                }
                R.id.nav_search -> {
                    fragment = SearchFragment()
                }
            }

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment!!)
                .commit()
            return@setOnItemSelectedListener true
        }

    }
}