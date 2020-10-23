import 'package:flutter_app/collection_link.dart';

class CurrentlyCollection {
  final String id;
  final double time;
  final String summary;
  final double nearestStormDistance;
  final double nearestStormBearing;
  final double precipIntensity;
  final double precipProbability;
  final double temperature;
  final double apparentTemperature;
  final double dewPoint;
  final double humidity;
  final double pressure;
  final double windSpeed;
  final double windGust;
  final double windBearing;
  final double cloudCover;
  final double uvIndex;
  final double visibility;
  final double ozone;
  final DateTime formattedTime;
  final List<CollectionLink> links;

  CurrentlyCollection({this.id, this.time, this.summary, this.nearestStormDistance, this.nearestStormBearing, this.precipIntensity, this.precipProbability, this.temperature,
    this.apparentTemperature, this.dewPoint, this.humidity, this.pressure, this.windSpeed, this.windGust, this.windBearing, this.cloudCover, this.uvIndex, this.visibility,
    this.ozone, this.formattedTime, this.links});

  factory CurrentlyCollection.fromJson(Map<String, dynamic> json) {
    List<dynamic> dynamicList = json['_links'];
    List<CollectionLink> links = List<CollectionLink>();
    Iterator itr = dynamicList.iterator;
    while(itr.moveNext()) {
      dynamic obj = itr.current;
      String name = obj;
      Uri uri = obj[0] as Uri;
      CollectionLink link = CollectionLink(name: name, uri: uri);
      links.add(link);
    }
    // probs wont work
    // json['links'].forEach((link) => links.add(new CollectionLink()));

    return CurrentlyCollection(
      id: json['id'] as String,
      time: json['time'] as double,
      summary: json['summary'] as String,
      nearestStormDistance: json['nearestStormDistance'] as double,
      precipIntensity: json['precipIntensity'] as double,
      precipProbability: json['precipProbability'] as double,
      temperature: json['temperature'] as double,
      apparentTemperature: json['apparentTemperature'] as double,
      dewPoint: json['dewPoint'] as double,
      humidity: json['humidity'] as double,
      pressure: json['pressure'] as double,
      windSpeed: json['windSpeed'] as double,
      windGust: json['windGust'] as double,
      windBearing: json['windBearing'] as double,
      cloudCover: json['cloudCover'] as double,
      uvIndex: json['uvIndex'] as double,
      visibility: json['visibility'] as double,
      ozone: json['ozone'] as double,
      formattedTime: json['formattedTime'] as DateTime,
      links: links,
    );
  }

  static CollectionLink decodeLinks(Map<String, dynamic> json) {
    String name = json['self'];
    Uri url = json['email'];
    DateTime dob = DateTime.parse(json['dob']);
    Student s = new Student(name, email, dob);
    return s;
  }
}
