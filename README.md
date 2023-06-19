# Flutter Touch Interaction Controller

[![pub package](https://img.shields.io/pub/v/flutter_touch_interaction_controller.svg)](https://pub.dev/packages/flutter_touch_interaction_controller)

  - **Description**:  A Flutter plugin to detect and handle touch events on the screen for Android device.
  - **Technology stack**: Flutter plugin.
  - **Status**:  [CHANGELOG](CHANGELOG.md).

## Main Features
  1. One-touch event recognition and stream provision.
  2. Requesting accessibilityService permission.
  3. Verifying accessibilityService permission.
  4. One-touch execution.

**Screenshot**: Sample screenshot of the project,

![](https://github.com/rabbitson87/flutter_touch_interaction_controller/blob/main/demo.gif)

## Installation

### Add dependencies

Add the following dependency to your pubspec.yaml file:

```dart
    dependencies:
    flutter_touch_interaction_controller: ^1.0.1
```

### Change Android settings

1. Add the following code after the activity section in the android/app/src/main/AndroidManifest.xml file:

```xml
<service
    android:name="com.rabbitson87.flutter_touch_interaction_controller.AccessibilityServicePlugin"
    android:exported="true"
    android:permission="android.permission.BIND_ACCESSIBILITY_SERVICE">
    <intent-filter>
        <action android:name="android.accessibilityservice.AccessibilityService" />
    </intent-filter>
    <meta-data
        android:name="android.accessibilityservice"
        android:resource="@xml/accessibility_service_config" />
</service>
```

2. Create android/app/src/main/res/xml/accessibility_service_config.xml file and add the following code:

```xml
<accessibility-service xmlns:android="http://schemas.android.com/apk/res/android"
    android:accessibilityEventTypes="typeAllMask"
    android:accessibilityFeedbackType="feedbackSpoken"
    android:accessibilityFlags="flagDefault"
    android:canPerformGestures="true"
    android:canRequestTouchExplorationMode="true"
    android:notificationTimeout="100" />
```

## Usage

Check if the accessibility permission is enabled:

```dart
bool isAccessibilityPermissionEnabled = await FlutterTouchInteractionController.isAccessibilityPermissionEnabled;
```

Request accessibility permission:

```dart
await FlutterTouchInteractionController.requestAccessibilityPermission;
```

Listen to the touch events:

```dart
final accessStream = FlutterTouchInteractionController.accessStream.listen((event) {
  print(event);
});
```

Remove the listener when it is no longer needed:

```dart
accessStream.cancel();
```

Execute a touch event:

```dart
await FlutterTouchInteractionController.touch(Point(x: 350, y: 400));
```

## Getting help

If you have questions, concerns, bug reports, etc, please file an issue in this repository's Issue Tracker.

## Getting involved

General instructions on _how_ to contribute should be stated with a link to [CONTRIBUTING](CONTRIBUTING.md).

----

## Open source licensing info
1. [TERMS](TERMS.md)
2. [LICENSE](LICENSE)

----

## Credits and references

1. [flutter_accessibility_service](https://github.com/X-SLAYER/flutter_accessibility_service)
2. [do-touch](https://github.com/princesanjivy/do-touch)
