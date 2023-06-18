#### CFPB Open Source Project Template Instructions

1. Create a new project.
2. [Copy these files into the new project](#installation)
3. Update the README, replacing the contents below as prescribed.
4. Add any libraries, assets, or hard dependencies whose source code will be included
   in the project's repository to the _Exceptions_ section in the [TERMS](TERMS.md).
  - If no exceptions are needed, remove that section from TERMS.
5. If working with an existing code base, answer the questions on the [open source checklist](opensource-checklist.md)
6. Delete these instructions and everything up to the _Project Title_ from the README.
7. Write some great software and tell people about it.

> Keep the README fresh! It's the first thing people see and will make the initial impression.

## Installation

To install all of the template files, run the following script from the root of your project's directory:

```
bash -c "$(curl -s https://raw.githubusercontent.com/CFPB/development/main/open-source-template.sh)"
```

----

# Flutter Touch Interaction Controller
    
[![Build Status](https://travis-ci.com/cfpb/flutter_touch_interaction_controller.svg?branch=main)](https://travis-ci.com/cfpb/flutter_touch_interaction_controller)
[![codecov](https://codecov.io/gh/cfpb/flutter_touch_interaction_controller/branch/main/graph/badge.svg)](https://codecov.io/gh/cfpb/flutter_touch_interaction_controller)
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

![](https://raw.githubusercontent.com/cfpb/open-source-project-template/main/screenshot.png)


## Dependencies

Describe any dependencies that must be installed for this software to work.
This includes programming languages, databases or other storage mechanisms, build tools, frameworks, and so forth.
If specific versions of other software are required, or known not to work, call that out.

## Installation

Detailed instructions on how to install, configure, and get the project running.
This should be frequently tested to ensure reliability. Alternatively, link to
a separate [INSTALL](INSTALL.md) document.

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

## How to test the software

```dart
flutter test
```

## Known issues

Document any known significant shortcomings with the software.

## Getting help

Instruct users how to get help with this software; this might include links to an issue tracker, wiki, mailing list, etc.

**Example**

If you have questions, concerns, bug reports, etc, please file an issue in this repository's Issue Tracker.

## Getting involved

This section should detail why people should get involved and describe key areas you are
currently focusing on; e.g., trying to get feedback on features, fixing certain bugs, building
important pieces, etc.

General instructions on _how_ to contribute should be stated with a link to [CONTRIBUTING](CONTRIBUTING.md).


----

## Open source licensing info
1. [TERMS](TERMS.md)
2. [LICENSE](LICENSE)
3. [CFPB Source Code Policy](https://github.com/cfpb/source-code-policy/)


----

## Credits and references

1. [flutter_accessibility_service](https://github.com/X-SLAYER/flutter_accessibility_service)
2. [do-touch](https://github.com/princesanjivy/do-touch)
