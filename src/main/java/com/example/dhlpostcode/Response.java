package com.example.dhlpostcode;

public record Response(String postcodeFrom, String latitudeFrom, String longitudeFrom, String postcodeTo, String latitudeTo, String longitudeTo, double distance, String unit) { }