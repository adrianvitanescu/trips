package com.sci.myTrips.controller;

import com.sci.myTrips.entity.Trip;
import com.sci.myTrips.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/trip")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping()
    public ResponseEntity<?> fetchTripsForUser(@RequestParam Long userId) {
        return ResponseEntity.ok(tripService.fetchTrips(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchTrip(@PathVariable Long id) {
        return ResponseEntity.ok().body(tripService.fetchTrip(id));
    }

    @PostMapping
    public ResponseEntity<?> createTrip(@RequestBody Trip trip) {
        return ResponseEntity.ok(tripService.saveTrip(trip));
    }

    @PutMapping
    public ResponseEntity<?> updateTrip(@RequestBody Trip trip) {
        return ResponseEntity.ok(tripService.updateTrip(trip));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTrip(@PathVariable Long id) {
         tripService.deleteTrip(id);
         return ResponseEntity.ok().body(id);
    }
}
