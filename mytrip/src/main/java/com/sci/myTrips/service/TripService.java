package com.sci.myTrips.service;

import com.sci.myTrips.entity.Trip;
import com.sci.myTrips.repo.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TripService {

    private final TripRepository repository;

    @Autowired
    public TripService(TripRepository repository) {
        this.repository = repository;
    }

    public Optional<Trip> fetchTrip(Long id) {
        return repository.findById(id);
    }

    public Trip saveTrip(Trip trip) {
        return repository.save(trip);
    }

    public ResponseEntity<?> updateTrip(Trip trip) {
        final Optional<Trip> tripOptional = repository.findById(trip.getId());

        if (tripOptional.isPresent()) {
            return ResponseEntity.ok(repository.save(trip));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(String.format("Trip with id %d not found", trip.getId()));
    }

    public void deleteTrip(Long id) {
        repository.deleteById(id);
    }

    public List<Trip> fetchTrips(Long id) {
        return repository.findAllByUserId(id);
    }
}
