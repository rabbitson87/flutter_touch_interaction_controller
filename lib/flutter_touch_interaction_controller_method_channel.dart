import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_touch_interaction_controller_platform_interface.dart';

/// An implementation of [FlutterTouchInteractionControllerPlatform] that uses method channels.
class MethodChannelFlutterTouchInteractionController extends FlutterTouchInteractionControllerPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_touch_interaction_controller');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
