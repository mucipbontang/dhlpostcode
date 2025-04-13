package com.example.dhlpostcode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostcodeController {
   private final PostcodeRepository repository;

    public PostcodeController(PostcodeRepository repository) {
        this.repository = repository;
    }


    /*@GetMapping("/get")
    public Response getController(@RequestParam(value = "postcodeFrom") String postcodeFrom, @RequestParam(value = "postcodeTo") String postcodeTo) {
        System.out.println("++getController");
        System.out.println("postcodeFrom = " + postcodeFrom);
        System.out.println("postcodeTo = " + postcodeTo);
        System.out.println("--getController");
        return new Response("B34", "52.4964133", "-1.7817039","IP10", "52.0189774", "1.2699895", 123456789.123, "KM");
    }*/

    @PostMapping("/post")
    public ResponseEntity postController(@RequestBody RequestPost requestPost) {
        System.out.println("++postController");
        System.out.println(requestPost);
        System.out.println("postcodeFrom = " + requestPost.postcodeFrom());
        System.out.println("postcodeTo = " + requestPost.postcodeTo());

        Postcode getPostcodeFrom = null;
        getPostcodeFrom = repository.findByCode(requestPost.postcodeFrom());
        Postcode getPostcodeTo = null;
        getPostcodeTo = repository.findByCode(requestPost.postcodeTo());

        if(getPostcodeFrom != null && getPostcodeTo != null) {
            System.out.println("getPostcodeFrom = " + getPostcodeFrom.toString());
            System.out.println("getPostcodeTo = " + getPostcodeTo.toString());

            System.out.println("--postController");

            return ResponseEntity.status(HttpStatus.FOUND).body(new Response(getPostcodeFrom.getCode(), getPostcodeFrom.getLatitude(), getPostcodeFrom.getLongitude()
                    ,getPostcodeTo.getCode(), getPostcodeTo.getLatitude(), getPostcodeTo.getLongitude()
                    , calculateDistance(Double.parseDouble(getPostcodeFrom.getLatitude()), Double.parseDouble(getPostcodeFrom.getLongitude()), Double.parseDouble(getPostcodeTo.getLatitude()), Double.parseDouble(getPostcodeTo.getLongitude()))
                    , "KM"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNotFound("NOT FOUND", "Invalid input postcode/s"));
        }
    }


    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2){
        System.out.println("++calculateDistance");
        final double NAUTICAL_MILES = 1.1515;
        final double KM_IN_MILES = 1.609344;

        double theta=lon1-lon2;
        double dist=Math.acos(
                Math.sin(Math.toRadians(lat1)) *
                        Math.sin(Math.toRadians(lat2)) +
                        Math.cos(Math.toRadians(lat1)) *
                                Math.cos(Math.toRadians(lat2)) *
                                Math.cos(Math.toRadians(theta))
        );

        dist=Math.toDegrees(dist);
        dist=dist*60;
        System.out.println("--calculateDistance");
        return dist * NAUTICAL_MILES * KM_IN_MILES;
    }
}