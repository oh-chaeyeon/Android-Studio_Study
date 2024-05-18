import 'package:flutter/material.dart';
import 'package:flutter_driver/driver_extension.dart';

void main() {
  enableFlutterDriverExtension();
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('List View Example'),
      ),
      body: ListView.builder(
        itemCount: 5,
        itemBuilder: (context, index) {
          return ListTile(
            title: Text('list_item${index + 1}', key: Key('list_item${index + 1}')),
            onTap: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => SubPage('list_item${index + 1}', 'list_item${index + 1}'),
                ),
              );
            },
          );
        },
      ),
    );
  }
}

class SubPage extends StatelessWidget {
  String title;
  String subtitle;

  SubPage(this.title, this.subtitle);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text(title, key: Key('title'))),
      body: Center(
        child: Text(subtitle),
      ),
    );
  }
}
