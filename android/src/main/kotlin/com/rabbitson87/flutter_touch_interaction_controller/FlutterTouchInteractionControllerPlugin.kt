package com.rabbitson87.flutter_touch_interaction_controller

import MessageApi
import Version

import io.flutter.embedding.engine.plugins.FlutterPlugin

private class MethodCallHandlerImplementation: MessageApi {
    override fun getPlatformVersion(): Version {
        return Version("Android ${android.os.Build.VERSION.RELEASE}")
    }
}
/** FlutterTouchInteractionControllerPlugin */
class FlutterTouchInteractionControllerPlugin : FlutterPlugin {
    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        MessageApi.setUp(flutterPluginBinding.binaryMessenger, MethodCallHandlerImplementation())
    }

    override fun onDetachedFromEngine( binding: FlutterPlugin.FlutterPluginBinding) {
        MessageApi.setUp(binding.binaryMessenger, null)
    }

}