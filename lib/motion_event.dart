part of flutter_touch_interaction_controller;

class MotionEvent {
  /// the performed action that triggered this event
  /// https://developer.android.com/reference/kotlin/android/view/MotionEvent.html#getaction
  MotionAction? motionAction;

  /// the X coordinate of this event
  /// https://developer.android.com/reference/kotlin/android/view/MotionEvent.html#getx
  double? x;

  /// the Y coordinate of this event
  /// https://developer.android.com/reference/kotlin/android/view/MotionEvent.html#gety
  double? y;

  /// the type of tool with which this event is associated
  /// https://developer.android.com/reference/kotlin/android/view/MotionEvent.html#gettooltype
  ToolType? toolType;

  /// the time (in ms) when this event happened
  /// https://developer.android.com/reference/kotlin/android/view/MotionEvent.html#geteventtime
  DateTime? eventTime;

  MotionEvent({this.motionAction});

  MotionEvent.fromJson(Map<dynamic, dynamic> json) {
    motionAction = json[IntentName.MOTIONACTION.name] == null
        ? null
        : MotionAction.getByValue(json[IntentName.MOTIONACTION.name]);
    x = json[IntentName.X.name];
    y = json[IntentName.Y.name];
    toolType = json[IntentName.TOOLTYPE.name] == null
        ? null
        : ToolType.getByValue(json[IntentName.TOOLTYPE.name]);
    eventTime = json[IntentName.EVENTTIME.name] == null
        ? null
        : DateTime.fromMillisecondsSinceEpoch(json[IntentName.EVENTTIME.name]);
  }

  @override
  String toString() {
    return 'MotionEvent{motionAction: $motionAction, x: $x, y: $y, toolType: $toolType, eventTime: $eventTime}';
  }
}
