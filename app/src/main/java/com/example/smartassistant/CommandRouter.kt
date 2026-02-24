package com.example.smartassistant

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.provider.Settings

class CommandRouter {

    fun route(context: Context, command: ParsedCommand) {
        when (command.action) {
            "OPEN_NOTIFICATIONS" -> DeviceControlAccessibilityService.performGlobalAction(
                AccessibilityService.GLOBAL_ACTION_NOTIFICATIONS
            )

            "OPEN_QUICK_SETTINGS" -> DeviceControlAccessibilityService.performGlobalAction(
                AccessibilityService.GLOBAL_ACTION_QUICK_SETTINGS
            )

            "GO_HOME" -> DeviceControlAccessibilityService.performGlobalAction(
                AccessibilityService.GLOBAL_ACTION_HOME
            )

            "OPEN_SETTINGS" -> {
                context.startActivity(
                    Intent(Settings.ACTION_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }
    }
}
