import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_touch_interaction_controller/flutter_touch_interaction_controller.dart';
import 'package:flutter_touch_interaction_controller/flutter_touch_interaction_controller_platform_interface.dart';
import 'package:flutter_touch_interaction_controller/flutter_touch_interaction_controller_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterTouchInteractionControllerPlatform
    with MockPlatformInterfaceMixin
    implements FlutterTouchInteractionControllerPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterTouchInteractionControllerPlatform initialPlatform = FlutterTouchInteractionControllerPlatform.instance;

  test('$MethodChannelFlutterTouchInteractionController is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterTouchInteractionController>());
  });

  test('getPlatformVersion', () async {
    FlutterTouchInteractionController flutterTouchInteractionControllerPlugin = FlutterTouchInteractionController();
    MockFlutterTouchInteractionControllerPlatform fakePlatform = MockFlutterTouchInteractionControllerPlatform();
    FlutterTouchInteractionControllerPlatform.instance = fakePlatform;

    expect(await flutterTouchInteractionControllerPlugin.getPlatformVersion(), '42');
  });
}
