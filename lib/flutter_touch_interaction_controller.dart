
import 'flutter_touch_interaction_controller_platform_interface.dart';

class FlutterTouchInteractionController {
  Future<String?> getPlatformVersion() {
    return FlutterTouchInteractionControllerPlatform.instance.getPlatformVersion();
  }
}
