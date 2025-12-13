package com.example.recordkeeper.editrecord

import android.R
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.example.recordkeeper.databinding.ActivityEditRecordBinding
import java.io.Serializable

const val INTENT_EXTRA_SCREEN_DATA = "screen_data"
class EditRecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditRecordBinding

	private val screenData: ScreenData by lazy {
//		intent.getSerializableExtra("screen_data") as ScreenData

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getSerializableExtra(INTENT_EXTRA_SCREEN_DATA, ScreenData::class.java) as ScreenData
        } else {
			@Suppress("DEPRECATION")
			intent.getSerializableExtra(INTENT_EXTRA_SCREEN_DATA) as ScreenData
        }
    }

	private val recordPreferences by lazy {
		getSharedPreferences(screenData.sharedPreferencesName, MODE_PRIVATE)
	}
    override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityEditRecordBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.toolbar)
		supportActionBar?.setDisplayHomeAsUpEnabled(true)
		supportActionBar?.setDisplayShowHomeEnabled(true)
		supportActionBar?.setDisplayShowTitleEnabled(true)

		setupUI()
		displayRecord()

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

	private fun setupUI() {
		supportActionBar?.title = "${screenData.record} Record"
		binding.textInputRecord.hint = screenData.recordFieldHint
		binding.buttonSave.setOnClickListener {
			saveRecord()
			finish()
		}
		binding.buttonDelete.setOnClickListener {
			clearRecord()
			finish()
		}
	}

	private fun clearRecord() {
		recordPreferences.edit {
			remove("${screenData.record} record")
			remove("${screenData.record} date")
		}
	}

	private fun displayRecord() {
		binding.editTextRecord.setText(recordPreferences.getString("${screenData.record} record", null))
		binding.editTextDate.setText(recordPreferences.getString("${screenData.record} date", null))

	}

	private fun saveRecord() {
		val record = binding.editTextRecord.text.toString()
		val date = binding.editTextDate.text.toString()
//		val editor = runningPreferences.edit()
//		editor.putString("record", record)
//		editor.putString("date", date)
//		editor.apply()
		// modern way
		recordPreferences.edit {
			putString("${this@EditRecordActivity.screenData.record} record", record)
			putString("${this@EditRecordActivity.screenData.record} date", date)
		}
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.home) {
			onBackPressedDispatcher.onBackPressed()
			return true
		}
		return super.onOptionsItemSelected(item)
	}

	data class ScreenData (
		val record: String,
		val sharedPreferencesName: String,
		val recordFieldHint: String,
	) : Serializable
}