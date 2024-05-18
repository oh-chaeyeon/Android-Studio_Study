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
      home: StartPage(),
    );
  }
}

class StartPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Start Page')),
      body: MyBody(),
    );
  }
}

class MyBody extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return ListView(
      children: [
        _tile(context, 'list_item1', 'Roxie Theater', '3117 16th St', Icons.theaters),
        _tile(context, 'list_item2', 'United Artists Stonestown Twin', '501 Buckingham Way', Icons.theaters),
        _tile(context, 'list_item3', 'AMC Metreon 16', '135 4th St #3000', Icons.theaters),
        const Divider(),
        _tile(context, 'list_item4', 'K\'s mouse', '757 Monterey Blvd', Icons.restaurant),
        _tile(context, 'list_item5', 'La Ciccia', '291 30th St', Icons.restaurant),
      ],
    );
  }

  Widget _tile(BuildContext context, String keyString, String title, String subtitle, IconData icon) {
    return ListTile(
      key: Key(keyString),
      title: Text(title, style: const TextStyle(fontWeight: FontWeight.w500, fontSize: 15)),
      subtitle: Text(subtitle),
      leading: Icon(icon, color: Colors.blue.shade500),
      onTap: () {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => SubPage(title, subtitle),
          ),
        );
      },
    );
  }
}

class SubPage extends StatelessWidget {
  final String title;
  final String subtitle;

  SubPage(this.title, this.subtitle);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text(title, key: Key('title'))),
        body: Text(subtitle)
    );
  }
}