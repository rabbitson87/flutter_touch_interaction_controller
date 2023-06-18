package com.rabbitson87.flutter_touch_interaction_controller

import MessageApi
import EventChannelName
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.PluginRegistry


/** FlutterTouchInteractionControllerPlugin */
class FlutterTouchInteractionControllerPlugin : FlutterPlugin, ActivityAware,
    PluginRegistry.ActivityResultListener, EventChannel.StreamHandler {
    private var context: Context? = null
    private var activity: Activity? = null
    private var binding: FlutterPlugin.FlutterPluginBinding? = null
    private var eventChannel: EventChannel? = null
    private var motionEventListener: MotionEventListener? = null

    companion object {
        const val ACTION_ACCESSIBILITY_SETTINGS = 87
    }

    // FlutterPlugin start
    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        binding = flutterPluginBinding
        context = flutterPluginBinding.applicationContext
        eventChannel =
            EventChannel(flutterPluginBinding.binaryMessenger, EventChannelName.RABBITSON87.name)
        eventChannel?.setStreamHandler(this)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        MessageApi.setUp(binding.binaryMessenger, null)
        eventChannel?.setStreamHandler(null)
        eventChannel = null
    }
    // FlutterPlugin end

    // ActivityAware start
    override fun onAttachedToActivity(activityPluginBinding: ActivityPluginBinding) {
        activity = activityPluginBinding.activity
        if (binding == null || context == null) {
            return
        }
        MessageApi.setUp(binding!!.binaryMessenger, MethodsChannel(context!!, activity!!))
        activityPluginBinding.addActivityResultListener(this)
    }

    override fun onDetachedFromActivity() {
        activity = null
    }

    override fun onDetachedFromActivityForConfigChanges() {
        activity = null
    }

    override fun onReattachedToActivityForConfigChanges(activityPluginBinding: ActivityPluginBinding) {
        onAttachedToActivity(activityPluginBinding)
    }
    // ActivityAware end

    // PluginRegistry.ActivityResultListener start
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (requestCode == ACTION_ACCESSIBILITY_SETTINGS && context != null && activity != null) {
            return isAccessibilityPermissionEnabled(context!!)
        }
        return false
    }
    // PluginRegistry.ActivityResultListener end

    // EventChannel.StreamHandler start
    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        if (events != null && context != null && arguments.toString() == EventChannelName.MOTIONEVENT.name) {
            val intentFilter = IntentFilter()
            intentFilter.addAction(AccessibilityServicePlugin.MOTION_EVENT_INTENT)
            motionEventListener = MotionEventListener(events)
            context!!.registerReceiver(motionEventListener, intentFilter)

            requestTouchExploration(context!!, false)
        }
    }

    override fun onCancel(arguments: Any?) {
        if (motionEventListener != null && arguments.toString() == EventChannelName.MOTIONEVENT.name) {
            context!!.unregisterReceiver(motionEventListener)
            motionEventListener = null

            requestTouchExploration(context!!, true)
        }
    }
    // EventChannel.StreamHandler end

}