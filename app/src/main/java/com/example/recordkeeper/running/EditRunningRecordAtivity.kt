package com.example.recordkeeper.running

import android.R
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.recordkeeper.databinding.ActivityEditRunningRecordAtivityBinding

class EditRunningRecordAtivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRunningRecordAtivityBinding

	private lateinit var runningPreferences: SharedPreferences
	private  val distance: String? by lazy{
		intent.getStringExtra("Distance")
	}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
		binding = ActivityEditRunningRecordAtivityBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setSupportActionBar(binding.toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.setDisplayShowHomeEnabled(true)
		supportActionBar?.setDisplayShowTitleEnabled(true)

		runningPreferences = getSharedPreferences("running", Context.MODE_PRIVATE)


		supportActionBar?.title = "$distance Record"
		displayRecord()
		binding.buttonSave.setOnClickListener {
			saveRecord()
			finish()
		}

//// 	examples
//		val applicationPreferences = PreferenceManager.getDefaultSharedPreferences(this)
//
//		applicationPreferences.edit {
//			putString("some data", "Applications preference value")
//		}
//
//		val activityPreferences = getPreferences(Context.MODE_PRIVATE)
//
//		activityPreferences.edit {
//			putInt("ActivityData", 1)
//		}
//
//		val fileNamePreferences = getSharedPreferences("some shared preference file name", Context.MODE_PRIVATE)
//
//		fileNamePreferences.edit {
//			putBoolean("Some Preference file name data", false)
//		}
    }

	private fun displayRecord() {
		binding.editTextRecord.setText(runningPreferences.getString("$distance record", null))
		binding.editTextDate.setText(runningPreferences.getString("$distance date", null))

	}

	private fun saveRecord() {
		val record = binding.editTextRecord.text.toString()
		val date = binding.editTextDate.text.toString()
//		val editor = runningPreferences.edit()
//		editor.putString("record", record)
//		editor.putString("date", date)
//		editor.apply()
		// modern way
		runningPreferences.edit {
			putString("$distance record", record)
			putString("$distance date", date)
		}
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.home) {
			onBackPressedDispatcher.onBackPressed()
			return true
		}
		return super.onOptionsItemSelected(item)
	}
}