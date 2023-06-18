# Installation instructions

## Add dependencies

Add the following dependency to your pubspec.yaml file:

```dart
    dependencies:
    flutter_touch_interaction_controller: ^1.0.0
```

## Change Android settings

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