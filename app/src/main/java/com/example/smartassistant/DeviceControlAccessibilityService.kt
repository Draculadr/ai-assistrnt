package com.example.smartassistant

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import java.lang.ref.WeakReference

class DeviceControlAccessibilityService : AccessibilityService() {

    override fun onServiceConnected() {
        serviceRef = WeakReference(this)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) = Unit

    override fun onInterrupt() = Unit

    companion object {
        private var serviceRef: WeakReference<DeviceControlAccessibilityService>? = null

        fun performGlobalAction(action: Int): Boolean {
            return serviceRef?.get()?.performGlobalAction(action) == true
        }
    }
}
