package com.rabbitson87.flutter_touch_interaction_controller.listeners

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.flutter.plugin.common.EventChannel

class RequestAccessibilitySettingListener(_eventSink: EventChannel.EventSink) :
    BroadcastReceiver() {
    private var eventSink = _eventSink

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent != null) {
            if (intent.action == EventChannelName.REQUESTACCESSIBILITY.name) {
                eventSink.success(
                    intent.getBooleanExtra(
                        EventChannelName.REQUESTACCESSIBILITY.name,
                        false
                    )
                )
            }
        }
    }
}