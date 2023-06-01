package com.rabbitson87.flutter_touch_interaction_controller

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent

class AccessibilityServicePlugin: AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        print("onAccessibilityEvent")
        print(event.toString())
    }

    override fun onInterrupt() {
    }
}