import 'dart:async';

import 'messages.g.dart';

class FlutterTouchInteractionController {
  static final MessageApi _api = MessageApi();
  static Future<String> get platformVersion async {
    Version version = await _api.getPlatformVersion();
    return version.string!;
  }
}
