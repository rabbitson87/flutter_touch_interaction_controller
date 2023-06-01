import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_touch_interaction_controller_method_channel.dart';

abstract class FlutterTouchInteractionControllerPlatform extends PlatformInterface {
  /// Constructs a FlutterTouchInteractionControllerPlatform.
  FlutterTouchInteractionControllerPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterTouchInteractionControllerPlatform _instance = MethodChannelFlutterTouchInteractionController();

  /// The default instance of [FlutterTouchInteractionControllerPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterTouchInteractionController].
  static FlutterTouchInteractionControllerPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterTouchInteractionControllerPlatform] when
  /// they register themselves.
  static set instance(FlutterTouchInteractionControllerPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
