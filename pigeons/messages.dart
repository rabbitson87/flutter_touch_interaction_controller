import 'package:pigeon/pigeon.dart';

@ConfigurePigeon(PigeonOptions(
  dartOut: 'lib/messages.g.dart',
  kotlinOut:
      'android/src/main/kotlin/com/rabbitson87/flutter_touch_interaction_controller/Messages.g.kt',
  copyrightHeader: 'pigeons/copyright.txt',
))
class Version {
  String? string;
}

@HostApi()
abstract class MessageApi {
  Version getPlatformVersion();
}
