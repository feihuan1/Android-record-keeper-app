package com.example.recordkeeper

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.recordkeeper.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonCycling.setOnClickListener { onCyclingClicked() }
        binding.buttonRunning.setOnClickListener { onRunningClicked() }
    }
}

private fun MainActivity.onRunningClicked() {
    supportFragmentManager.commit {
        replace(R.id.frame_content, RunningFragment())
    }
}

private fun MainActivity.onCyclingClicked() {
    supportFragmentManager.commit {
        replace(R.id.frame_content, CyclingFragment())
    }
}
