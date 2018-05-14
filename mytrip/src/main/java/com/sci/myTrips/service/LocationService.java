package com.sci.myTrips.service;

import com.sci.myTrips.entity.Location;
import com.sci.myTrips.repo.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService implements ILocationService {

    @Autowired
    private LocationRepository repository;

    @Override
    public List<Location> findAll() {

        List<Location> location = (List<Location>) repository.findAll();
        return location;
    }
}