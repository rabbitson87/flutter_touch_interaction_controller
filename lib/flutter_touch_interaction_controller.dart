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

  static Future<bool> requestAccessibilityPermission() async =>
      await _api.requestAccessibilityPermission();

  static Future<bool> touch(Point point) async => await _api.touch(point);

  static final EventChannel _eventChannel =
      EventChannel(EventChannelName.RABBITSON87.name);

  static Stream<MotionEvent> get accessStream {
    if (Platform.isAndroid) {
      return _eventChannel
          .receiveBroadcastStream(EventChannelName.MOTIONEVENT.name)
          .map<MotionEvent>(
            (event) => MotionEvent.fromJson(event),
          );
    }
    throw Exception("Accessibility API exclusively available on Android!");
  }
}
