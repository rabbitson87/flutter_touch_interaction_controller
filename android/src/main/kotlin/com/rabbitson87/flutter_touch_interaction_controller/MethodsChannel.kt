package com.rabbitson87.flutter_touch_interaction_controller

import MessageApi
import Point
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils.SimpleStringSplitter


fun isAccessibilityPermissionEnabled(context: Context): Boolean {
    val expectedComponentName = ComponentName(context, AccessibilityServicePlugin::class.java)

    val enabledServicesSetting: String =
        Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
            ?: return false

    val colonSplitter = SimpleStringSplitter(':')
    colonSplitter.setString(enabledServicesSetting)

    while (colonSplitter.hasNext()) {
        val componentNameString = colonSplitter.next()
        val enabledService = ComponentName.unflattenFromString(componentNameString)
        if (enabledService != null && enabledService == expectedComponentName) return true
    }

    return false
}

fun requestTouchExploration(context: Context, status: Boolean) {
    val intent = Intent(context, AccessibilityServicePlugin::class.java)
    intent.putExtra(AccessibilityServicePlugin.TOUCH_EXPLORATION, status)
    context.startService(intent)
}

class MethodsChannel(context: Context, activity: Activity): MessageApi {
    private var _context: Context = context
    private var _activity: Activity = activity

    override fun isAccessibilityPermissionEnabled(): Boolean {
        return isAccessibilityPermissionEnabled(_context)
    }

    override fun requestAccessibilityPermission(): Boolean {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        _activity.startActivityForResult(intent, FlutterTouchInteractionControllerPlugin.ACTION_ACCESSIBILITY_SETTINGS)
        return true
    }

    override fun touch(point: Point): Boolean {
        val position = FloatArray(2)
        position[0]= point.x.toFloat()
        position[1] = point.y.toFloat()

        val intent = Intent(_context, AccessibilityServicePlugin::class.java)
        intent.putExtra(AccessibilityServicePlugin.POSITION, position)
        _context.startService(intent)
        return true
    }

}