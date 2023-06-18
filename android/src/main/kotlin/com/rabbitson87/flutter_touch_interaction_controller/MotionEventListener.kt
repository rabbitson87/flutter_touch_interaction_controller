package com.rabbitson87.flutter_touch_interaction_controller

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.flutter.plugin.common.EventChannel
import java.util.HashMap

class MotionEventListener(_eventSink: EventChannel.EventSink) : BroadcastReceiver() {
    private var eventSink = _eventSink

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null && intent.action == AccessibilityServicePlugin.MOTION_EVENT_INTENT) {
            /// Send data back via the Event Sink
            val data: HashMap<String, Any?> = HashMap()

            data[IntentName.MOTIONACTION.name] =
                intent.getIntExtra(IntentName.MOTIONACTION.name, -1)

            data[IntentName.X.name] = intent.getFloatExtra(IntentName.X.name, -1f)

            data[IntentName.Y.name] = intent.getFloatExtra(IntentName.Y.name, -1f)

            data[IntentName.TOOLTYPE.name] = intent.getIntExtra(IntentName.TOOLTYPE.name, -1)

            data[IntentName.EVENTTIME.name] = intent.getLongExtra(IntentName.EVENTTIME.name, -1)

            eventSink.success(data)
        }
    }
}