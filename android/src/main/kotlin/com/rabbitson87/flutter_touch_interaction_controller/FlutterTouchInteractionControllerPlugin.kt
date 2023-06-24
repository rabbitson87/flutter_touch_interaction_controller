package com.rabbitson87.flutter_touch_interaction_controller

import MessageApi
import EventChannelName
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.rabbitson87.flutter_touch_interaction_controller.listeners.MotionEventListener
import com.rabbitson87.flutter_touch_interaction_controller.listeners.RequestAccessibilitySettingListener
import com.rabbitson87.flutter_touch_interaction_controller.listeners.SwipeEventListener
import com.rabbitson87.flutter_touch_interaction_controller.listeners.TouchEventListener
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
    private var eventChannel: HashMap<String, EventChannel?> = HashMap()
    private var eventListener: HashMap<String, BroadcastReceiver?> = HashMap()
    private var eventListenerClass: HashMap<String, (events: EventChannel.EventSink) -> BroadcastReceiver> =
        HashMap()

    init {
        EventChannelName.values().forEach {
            eventChannel[it.name] = null
            eventListener[it.name] = null
        }

        eventListenerClass[EventChannelName.MOTION.name] =
            fun(events: EventChannel.EventSink): MotionEventListener {
                return MotionEventListener(
                    events
                )
            }
        eventListenerClass[EventChannelName.REQUESTACCESSIBILITY.name] =
            fun(events: EventChannel.EventSink): RequestAccessibilitySettingListener {
                return RequestAccessibilitySettingListener(
                    events
                )
            }
        eventListenerClass[EventChannelName.TOUCH.name] =
            fun(events: EventChannel.EventSink): TouchEventListener {
                return TouchEventListener(
                    events
                )
            }
        eventListenerClass[EventChannelName.SWIPE.name] =
            fun(events: EventChannel.EventSink): SwipeEventListener {
                return SwipeEventListener(
                    events
                )
            }
    }

    companion object {
        const val ACTION_ACCESSIBILITY_SETTINGS = 87
    }

    // FlutterPlugin start
    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        binding = flutterPluginBinding
        context = flutterPluginBinding.applicationContext

        EventChannelName.values().forEach {
            eventChannel[it.name] =
                EventChannel(flutterPluginBinding.binaryMessenger, it.name)
            eventChannel[it.name]?.setStreamHandler(this)
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        MessageApi.setUp(binding.binaryMessenger, null)

        EventChannelName.values().forEach {
            eventChannel[it.name]?.setStreamHandler(null)
            eventChannel[it.name] = null
        }
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
            val requestIntent = Intent(EventChannelName.REQUESTACCESSIBILITY.name)
            val result = isAccessibilityPermissionEnabled(context!!)

            requestIntent.putExtra(EventChannelName.REQUESTACCESSIBILITY.name, result)
            context!!.sendBroadcast(requestIntent)
            return result
        }
        return false
    }
    // PluginRegistry.ActivityResultListener end

    // EventChannel.StreamHandler start
    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        if (events != null && context != null && arguments != null && EventChannelName.values()
                .any { it.name == arguments.toString() }
        ) {
            val intentFilter = IntentFilter()
            val eventChannelName = EventChannelName.valueOf(arguments.toString())
            intentFilter.addAction(eventChannelName.name)
            eventListener[eventChannelName.name] =
                eventListenerClass[eventChannelName.name]?.let { it(events) }
            context!!.registerReceiver(eventListener[eventChannelName.name], intentFilter)

            if (eventChannelName == EventChannelName.MOTION) {
                requestTouchExploration(context!!, false)
            }
        }
    }

    private fun removeListener(eventChannelName: EventChannelName) {
        if (eventListener[eventChannelName.name] != null) {
            context!!.unregisterReceiver(eventListener[eventChannelName.name])
            eventListener[eventChannelName.name] = null
        }
    }

    override fun onCancel(arguments: Any?) {
        if (arguments != null && EventChannelName.values()
                .any { it.name == arguments.toString() }
        ) {
            val eventChannelName = EventChannelName.valueOf(arguments.toString())
            removeListener(eventChannelName)
            if (eventChannelName == EventChannelName.MOTION) {
                requestTouchExploration(context!!, true)
            }
        }
    }
    // EventChannel.StreamHandler end

}