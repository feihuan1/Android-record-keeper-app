package com.example.recordkeeper

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.recordkeeper.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.bottomNav.setOnItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        binding.toolbar.overflowIcon?.let { icon ->
            DrawableCompat.setTint(
                icon,
                ContextCompat.getColor(this, R.color.white)
            )
            binding.toolbar.overflowIcon = icon
        }
        return true
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean = when (menuItem.itemId) {
        R.id.nav_cycling -> onCyclingClicked()
        R.id.nav_running -> onRunningClicked()
        else -> false
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            R.id.reset_running -> {
                Toast.makeText(this, "Clicked reset running", Toast.LENGTH_LONG).show()
                true
            }

            R.id.reset_cycling -> {
                Toast.makeText(this, "Clicked reset cycling", Toast.LENGTH_LONG).show()
                true
            }

            R.id.reset_all -> {
                Toast.makeText(this, "Clicked reset all", Toast.LENGTH_LONG).show()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }


    private fun onRunningClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, RunningFragment())
        }
        return true
    }

    private fun onCyclingClicked(): Boolean {
        supportFragmentManager.commit {
            replace(R.id.frame_content, CyclingFragment())
        }
        return true
    }
}
