library flutter_touch_interaction_controller;

import 'dart:async';
import 'dart:io';

import 'package:flutter/services.dart';

import 'messages.g.dart';
export 'messages.g.dart' show Point;

part 'constant.dart';

part 'motion_event.dart';

class FlutterTouchInteractionController {
  static final MessageApi _api = MessageApi();

  static Future<bool> get isAccessibilityPermissionEnabled async =>
      await _api.isAccessibilityPermissionEnabled();

  static Stream<MotionEvent> get accessStream {
    if (Platform.isAndroid) {
      return EventChannel(EventChannelName.MOTION.name)
          .receiveBroadcastStream(EventChannelName.MOTION.name)
          .map<MotionEvent>(
            (event) => MotionEvent.fromJson(event),
          );
    }
    throw Exception("Accessibility API exclusively available on Android!");
  }

  static Future<bool> _getResultBool(EventChannelName eventChannelName) async {
    final completer = Completer<bool>();
    StreamSubscription<bool>? eventListener;
    if (Platform.isAndroid) {
      eventListener = EventChannel(eventChannelName.name)
          .receiveBroadcastStream(eventChannelName.name)
          .map<bool>(
            (event) => event,
          )
          .listen((event) {
        completer.complete(event);
        if (eventListener != null) {
          eventListener.cancel();
        }
      });
      return completer.future;
    }
    return false;
  }

  static Future<bool> requestAccessibilityPermission() async {
    final requestResult = await _api.requestAccessibilityPermission();
    return requestResult ? await _getResultBool(EventChannelName.REQUESTACCESSIBILITY) : false;
  }

  static Future<bool> touch(Point point) async {
    await _api.touch(point);
    return await _getResultBool(EventChannelName.TOUCH);
  }

  static Future<bool> swipe(Point startPoint, Point endPoint) async {
    await _api.swipe(startPoint, endPoint);
    return await _getResultBool(EventChannelName.SWIPE);
  }
}
