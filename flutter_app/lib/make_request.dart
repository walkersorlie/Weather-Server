import 'dart:async';
import 'dart:convert';
import 'dart:html';

import 'package:flutter/foundation.dart';
import 'package:flutter_app/currently_collection.dart';
import 'package:http/http.dart' as http;

Future<CurrentlyCollection> queryEndpoint(http.Client client, String url) async {
  final response = await client.get('http://192.168.0.98:8080/rest-service-0.0.1/api/' + url);

  // Use the compute function to run parsePhotos in a separate isolate.
  return compute(parseCollection, response.body);
}

// A function that converts a response body into a List<Photo>.
CurrentlyCollection parseCollection(String responseBody) {
  final parsed = jsonDecode(responseBody).cast<Map<String, dynamic>>();

  return CurrentlyCollection.fromJson(parsed);
}

