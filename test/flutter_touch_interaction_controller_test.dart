import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_touch_interaction_controller/flutter_touch_interaction_controller.dart';
import 'package:flutter_touch_interaction_controller/messages.g.dart';

const prefixName = 'dev.flutter.pigeon.MessageApi.';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();
  final TestDefaultBinaryMessenger binaryMessenger = TestDefaultBinaryMessengerBinding.instance.defaultBinaryMessenger;
  const MessageCodec<Object?> codec = MessageApi.codec;

  const String isAccessibilityPermissionEnabled = '${prefixName}isAccessibilityPermissionEnabled';
  const String requestAccessibilityPermission = '${prefixName}requestAccessibilityPermission';
  const String touch = '${prefixName}touch';

  setUp(() {
    binaryMessenger.setMockMessageHandler(
        isAccessibilityPermissionEnabled, (ByteData? message) async => codec.encodeMessage(<Object?>[true]));
    binaryMessenger.setMockMessageHandler(
        requestAccessibilityPermission, (ByteData? message) async => codec.encodeMessage(<Object?>[true]));
    binaryMessenger.setMockMessageHandler(touch, (ByteData? message) async => codec.encodeMessage(<Object?>[true]));
  });

  tearDown(() {
    binaryMessenger.setMockMessageHandler(isAccessibilityPermissionEnabled, null);
    binaryMessenger.setMockMessageHandler(requestAccessibilityPermission, null);
    binaryMessenger.setMockMessageHandler(touch, null);
  });

  test('isAccessibilityPermissionEnabled', () async {
    expect(await FlutterTouchInteractionController.isAccessibilityPermissionEnabled, true);
  });

  test('requestAccessibilityPermission', () async {
    expect(await FlutterTouchInteractionController.requestAccessibilityPermission(), true);
  });

  test('touch', () async {
    expect(await FlutterTouchInteractionController.touch(Point(x: 100, y: 100)), true);
  });

  // TODO: test touch event listener
}

// class FlutterTouchInteractionController {
//   static final MessageApi _api = MessageApi();
//
//   static Future<bool> get isAccessibilityPermissionEnabled async =>
//       await _api.isAccessibilityPermissionEnabled();
//
//   static Future<void> requestAccessibilityPermission() async {
//     await _api.requestAccessibilityPermission();
//   }
//
//   static Future<bool> touch(Point point) async => await _api.touch(point);
//
//   }
// }
