package com.rabbitson87.flutter_touch_interaction_controller

import IntentName
import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.accessibilityservice.GestureDescription
import android.accessibilityservice.GestureDescription.StrokeDescription
import android.accessibilityservice.TouchInteractionController
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.graphics.Region
import android.view.Display
import android.view.MotionEvent
import android.view.WindowManager
import android.view.WindowMetrics
import android.view.accessibility.AccessibilityEvent
import java.io.Serializable

class AccessibilityServicePlugin : AccessibilityService(), Serializable,
    TouchInteractionController.Callback {
    companion object {
        const val POSITION = "position"
        const val TOUCH_EXPLORATION = "touchExploration"
        const val MOTION_EVENT_INTENT = "motionEvent"
    }

    private var _flags = 0

    override fun onServiceConnected() {
        super.onServiceConnected()
        _flags = serviceInfo.flags
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (intent?.extras?.containsKey(POSITION) == true) {
            val position = intent.getFloatArrayExtra(POSITION)!!
            touch(position[0], position[1])
        }

        if (intent?.extras?.containsKey(TOUCH_EXPLORATION) == true) {
            val status = intent.getBooleanExtra(TOUCH_EXPLORATION, false)

            if (!status) {
                val info = serviceInfo
                info.flags =
                    _flags + AccessibilityServiceInfo.FLAG_REQUEST_TOUCH_EXPLORATION_MODE
                serviceInfo = info

                setTouchExplorationPassthroughRegion(
                    Display.DEFAULT_DISPLAY, getRegionOfFullScreen(
                        applicationContext
                    )
                )

                getTouchInteractionController(Display.DEFAULT_DISPLAY).registerCallback(null, this)
            } else {
                val info = serviceInfo
                info.flags = _flags
                serviceInfo = info

                setTouchExplorationPassthroughRegion(
                    Display.DEFAULT_DISPLAY, Region()
                )

                getTouchInteractionController(Display.DEFAULT_DISPLAY).unregisterAllCallbacks()
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun getRegionOfFullScreen(context: Context): Region {
        val metrics: WindowMetrics? =
            context.getSystemService(WindowManager::class.java)?.currentWindowMetrics
        return if (metrics == null) {
            Region()
        } else {
            Region(0, 0, metrics.bounds.width(), metrics.bounds.height())
        }
    }

    // TouchInteractionController.Callback start
    override fun onMotionEvent(motionEvent: MotionEvent) {
        val touchIntent = Intent(MOTION_EVENT_INTENT)

        val actionType: Int = motionEvent.action
        touchIntent.putExtra(IntentName.MOTIONACTION.name, actionType)

        val x: Float = motionEvent.x
        touchIntent.putExtra(IntentName.X.name, x)

        val y: Float = motionEvent.y
        touchIntent.putExtra(IntentName.Y.name, y)

        val toolType: Int = motionEvent.getToolType(0) // set to 0 for one finger
        touchIntent.putExtra(IntentName.TOOLTYPE.name, toolType)

        val eventTime: Long = System.currentTimeMillis()
        touchIntent.putExtra(IntentName.EVENTTIME.name, eventTime)

        sendBroadcast(touchIntent)
    }

    override fun onStateChanged(state: Int) {}
    // TouchInteractionController.Callback end

    private fun touch(x: Float, y: Float) {
        val gestureBuilder = GestureDescription.Builder()
        val path = Path()
        path.moveTo(x, y)
        gestureBuilder.addStroke(StrokeDescription(path, 0, 100, false))
        dispatchGesture(
            gestureBuilder.build(), object : GestureResultCallback() {
                override fun onCancelled(gestureDescription: GestureDescription) {
                    super.onCancelled(gestureDescription)
                }

                override fun onCompleted(gestureDescription: GestureDescription) {
                    super.onCompleted(gestureDescription)
                }
            }, null
        )
    }
}