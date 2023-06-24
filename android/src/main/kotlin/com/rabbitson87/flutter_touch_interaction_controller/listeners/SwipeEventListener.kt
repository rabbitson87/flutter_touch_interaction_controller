package com.rabbitson87.flutter_touch_interaction_controller.listeners

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.flutter.plugin.common.EventChannel

class SwipeEventListener(_eventSink: EventChannel.EventSink) :
    BroadcastReceiver() {
    private var eventSink = _eventSink

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            if (intent.action == EventChannelName.SWIPE.name) {
                eventSink.success(
                    intent.getBooleanExtra(
                        EventChannelName.SWIPE.name,
                        false
                    )
                )
            }
        }
    }
}