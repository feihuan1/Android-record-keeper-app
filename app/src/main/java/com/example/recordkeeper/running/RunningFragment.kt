package com.example.recordkeeper.running

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.recordkeeper.running.EditRunningRecordAtivity
import com.example.recordkeeper.databinding.FragmentRunningBinding
import com.example.recordkeeper.editrecord.EditRecordActivity

class RunningFragment : Fragment() {

    private lateinit var binding: FragmentRunningBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRunningBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListener()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
        displayRecords()
    }

    private fun setupClickListener() {
        binding.container5km.setOnClickListener { launchRunningRecordScreen("5km") }
        binding.container10km.setOnClickListener { launchRunningRecordScreen("10km") }
        binding.containerHalfMarathon.setOnClickListener { launchRunningRecordScreen("Half Marathon") }
        binding.containerMarathon.setOnClickListener { launchRunningRecordScreen("Marathon") }
    }

    private fun displayRecords() {
        val runningPreferences = requireContext().getSharedPreferences("running", Context.MODE_PRIVATE)

        binding.textView5kmValue.text = runningPreferences.getString("5km record", "No record")
        binding.textView5kmDate.text = runningPreferences.getString("5km date", "No date")
        binding.textView10kmValue.text = runningPreferences.getString("10km record", "No record")
        binding.textView10kmDate.text = runningPreferences.getString("10km date", "No date")
        binding.textViewHalfMarathonValue.text = runningPreferences.getString("Half Marathon", "No record")
        binding.textViewHalfMarathonDate.text = runningPreferences.getString("Half Marathon", "No date")
        binding.textViewMarathonValue.text = runningPreferences.getString("Marathon", "No record")
        binding.textViewMarathonDate.text = runningPreferences.getString("Marathon", "No date")
    }

    private fun launchRunningRecordScreen(distance: String) {
        val intent = Intent(context, EditRecordActivity::class.java)
        intent.putExtra("screen_data", EditRecordActivity.ScreenData(distance, "running", "Time"))
        startActivity(intent)
    }
}