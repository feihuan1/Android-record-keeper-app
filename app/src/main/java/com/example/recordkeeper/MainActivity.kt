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
                showConfirmationDialog(RUNNING_DISPLAY_VALUE)
                true
            }

            R.id.reset_cycling -> {
                showConfirmationDialog(CYCLING_DISPLAY_VALUE)
                true
            }

            R.id.reset_all -> {
                showConfirmationDialog(ALL_DISPLAY_VALUE)
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
                    ALL_DISPLAY_VALUE -> {
                        getSharedPreferences(RunningFragment.FILENAME, MODE_PRIVATE).edit { clear() }
                        getSharedPreferences(CyclingFragment.FILENAME, MODE_PRIVATE).edit { clear() }
                    }
                    RUNNING_DISPLAY_VALUE -> {
                        getSharedPreferences(RunningFragment.FILENAME, MODE_PRIVATE).edit { clear() }
                    }
                    CYCLING_DISPLAY_VALUE -> {
                        getSharedPreferences(CyclingFragment.FILENAME, MODE_PRIVATE).edit { clear() }
                    }
                    else -> {}
                }
                refreshCurrentFragment()
                showConfirmation()
            }
            .setNegativeButton("No", null)
            .show()
    }

    private fun showConfirmation() {
        val snackBar = Snackbar.make(
            binding.frameContent,
            "Records cleared successfully!",
            Snackbar.LENGTH_LONG
        )
        snackBar.anchorView = binding.bottomNav
        snackBar.show()
        snackBar.setAction("Undo") {
            // restore maybe
        }
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

    companion object {
        const val RUNNING_DISPLAY_VALUE = "running"
        const val CYCLING_DISPLAY_VALUE = "cycling"
        const val ALL_DISPLAY_VALUE = "all"
    }
}
