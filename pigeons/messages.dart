import 'package:pigeon/pigeon.dart';

@ConfigurePigeon(PigeonOptions(
  dartOut: 'lib/messages.g.dart',
  kotlinOut: 'android/src/main/kotlin/com/rabbitson87/flutter_touch_interaction_controller/Messages.g.kt',
))

class Point {
  double x;
  double y;

  Point(this.x, this.y);
}

enum IntentName {
  MOTIONACTION,
  X,
  Y,
  TOOLTYPE,
  EVENTTIME,
}

enum EventChannelName {
  MOTION,
  REQUESTACCESSIBILITY,
  TOUCH,
  SWIPE,
}

@HostApi()
abstract class MessageApi {
  bool isAccessibilityPermissionEnabled();
  bool requestAccessibilityPermission();
  bool touch(Point point);
  bool swipe(Point startPoint, Point endPoint);
}
