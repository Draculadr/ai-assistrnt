package com.example.smartassistant

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class VoiceAssistantService : Service() {

    private val commandRouter = CommandRouter()

    override fun onCreate() {
        super.onCreate()
        startForeground(NOTIFICATION_ID, buildNotification("Assistant is listening"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Placeholder: connect SpeechRecognizer or offline ASR engine.
        val spokenText = intent?.getStringExtra(EXTRA_MOCK_COMMAND) ?: "open notifications"
        val parsedCommand = NativeCommandEngine.parseCommand(spokenText)
        commandRouter.route(this, parsedCommand)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun buildNotification(content: String): Notification {
        val channelId = "assistant_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Smart Assistant",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
        return NotificationCompat.Builder(this, channelId)
            .setContentTitle("Smart Assistant")
            .setContentText(content)
            .setSmallIcon(android.R.drawable.ic_btn_speak_now)
            .build()
    }

    companion object {
        const val EXTRA_MOCK_COMMAND = "mock_command"
        private const val NOTIFICATION_ID = 1001
    }
}
