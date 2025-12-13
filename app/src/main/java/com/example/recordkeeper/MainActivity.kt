package com.example.recordkeeper

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.graphics.drawable.DrawableCompat
import com.example.recordkeeper.cycling.CyclingFragment
import com.example.recordkeeper.databinding.ActivityMainBinding
import com.example.recordkeeper.running.RunningFragment
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar

const val RUNNING = "running"
const val CYCLING = "cycling"

const val ALL = "all"

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val menuClickedHandled = when (item.itemId) {
            R.id.reset_running -> {
                showConfirmationDialog(RUNNING)
                true
            }

            R.id.reset_cycling -> {
                showConfirmationDialog(CYCLING)
                true
            }

            R.id.reset_all -> {
                showConfirmationDialog(ALL)
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }

        return menuClickedHandled
    }

    private fun showConfirmationDialog(selection: String) {
        AlertDialog.Builder(this)
            .setTitle("Reset $selection records")
            .setMessage("Are you sure you want clear the records")
            .setPositiveButton(
                "yes"
            ) { _, _ ->
                when (selection) {
                    ALL -> {
                        getSharedPreferences(RUNNING, MODE_PRIVATE).edit { clear() }
                        getSharedPreferences(CYCLING, MODE_PRIVATE).edit { clear() }
                    }
                    else -> {
                        getSharedPreferences(selection, MODE_PRIVATE).edit { clear() }
                    }
                }
                refreshCurrentFragment()
                val snackBar = Snackbar.make(binding.frameContent, "Records cleared successfully!", Snackbar.LENGTH_LONG)
                snackBar.anchorView = binding.bottomNav
                snackBar.show()
                snackBar.setAction("Undo") {
                    // restore maybe
                }
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun refreshCurrentFragment() {
        when (binding.bottomNav.selectedItemId) {
            R.id.nav_running -> onRunningClicked()
            R.id.nav_cycling -> onCyclingClicked()
            else -> {}
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
