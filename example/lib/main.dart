import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_touch_interaction_controller/flutter_touch_interaction_controller.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  bool _isAccessibilityPermissionEnabled = false;
  StreamSubscription<MotionEvent>? _subscription;
  List<MotionEvent?> events = [];

  @override
  void initState() {
    super.initState();
  }

  Future<void> _isAccessibilityPermissionEnabledState() async {
    bool isAccessibilityPermissionEnabled;
    try {
      isAccessibilityPermissionEnabled = await FlutterTouchInteractionController
          .isAccessibilityPermissionEnabled;
    } on PlatformException {
      isAccessibilityPermissionEnabled = false;
    }

    if (!mounted) return;

    setState(() {
      _isAccessibilityPermissionEnabled = isAccessibilityPermissionEnabled;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('FlutterTouchInteractionController example'),
        ),
        body: Center(
          child: Column(children: [
            ElevatedButton(
              onPressed: () async {
                final result = await FlutterTouchInteractionController
                    .requestAccessibilityPermission();
                print("requestAccessibilityPermission: $result");
              },
              child: const Text('Request Accessibility Permission'),
            ),
            ElevatedButton(
              onPressed: _isAccessibilityPermissionEnabledState,
              child: const Text('Is Accessibility Permission Enabled'),
            ),
            Text(
                'Is Accessibility Permission Enabled: $_isAccessibilityPermissionEnabled'),
            ElevatedButton(
              onPressed: () {
                if (_subscription == null) {
                  setState(() {
                    _subscription = FlutterTouchInteractionController
                        .accessStream
                        .listen((event) {
                      print("$event");
                      setState(() {
                        events.add(event);
                      });
                    });
                  });
                } else {
                  _subscription?.cancel();
                  _subscription = null;
                  setState(() {
                    events = [];
                  });
                }
              },
              child: Text(
                  _subscription == null ? 'Start Listening' : 'Stop Listening'),
            ),
            ElevatedButton(
              onPressed: () async {
                final touchResult = await FlutterTouchInteractionController.touch(
                    Point(x: 350, y: 400));
                print("touchResult: $touchResult");
              },
              child: const Text('Click'),
            ),
            ElevatedButton(
              onPressed: () async {
                final touchResult = await FlutterTouchInteractionController.swipe(
                    Point(x: 350, y: 1400), Point(x: 350, y: 1000));
                print("SwipeResult: $touchResult");
              },
              child: const Text('Swipe'),
            ),
            Expanded(
              child: ListView.builder(
                shrinkWrap: true,
                itemCount: events.length,
                itemBuilder: (_, index) => ListTile(
                  title: Text(events[index]!.toString()),
                ),
              ),
            ),
          ]),
        ),
      ),
    );
  }
}
