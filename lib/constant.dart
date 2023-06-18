part of flutter_touch_interaction_controller;

enum MotionAction {
  actionCancel('actionCancel', 3),
  actionDown('actionDown', 0),
  actionHoverEnter('actionHoverEnter', 9),
  actionHoverExit('actionHoverExit', 10),
  actionHoverMove('actionHoverMove', 7),
  actionMask('actionMask', 255),
  actionMove('actionMove', 2),
  actionOutside('actionOutside', 4),
  actionPointerUp('actionPointerUp', 6),
  actionScroll('actionScroll', 8),
  actionUp('actionUp', 1),
  undefined('undefined', -1);

  const MotionAction(this.name, this.value);

  final String name;
  final int value;

  factory MotionAction.getByValue(int value) {
    return MotionAction.values.firstWhere((e) => e.value == value, orElse: () => MotionAction.undefined);
  }
}

enum ToolType {
  toolTypeEraser('toolTypeEraser', 4),
  toolTypeFinger('toolTypeFinger', 1),
  toolTypeMouse('toolTypeMouse', 3),
  toolTypeStylus('toolTypeStylus', 2),
  toolTypeUnknown('toolTypeUnknown', 0);

  const ToolType(this.name, this.value);

  final String name;
  final int value;

  factory ToolType.getByValue(int value) {
    return ToolType.values.firstWhere((e) => e.value == value, orElse: () => ToolType.toolTypeUnknown);
  }
}