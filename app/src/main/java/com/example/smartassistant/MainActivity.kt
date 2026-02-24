package com.example.smartassistant

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import com.example.smartassistant.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    private val requestPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        val message = if (granted) {
            "Microphone permission granted"
        } else {
            "Microphone permission is required for voice access"
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startAssistantButton.setOnClickListener {
            requestVoicePermissionIfNeeded()
            startForegroundService(Intent(this, VoiceAssistantService::class.java))
        }

        binding.stopAssistantButton.setOnClickListener {
            stopService(Intent(this, VoiceAssistantService::class.java))
        }

        binding.openAccessibilitySettingsButton.setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
    }

    private fun requestVoicePermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermission.launch(Manifest.permission.RECORD_AUDIO)
        }
    }
}
