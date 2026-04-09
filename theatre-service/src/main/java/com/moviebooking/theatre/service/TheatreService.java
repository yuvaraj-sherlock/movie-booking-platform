package com.moviebooking.theatre.service;

import com.moviebooking.theatre.entity.Theatre;
import com.moviebooking.theatre.repository.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepository repository;

    public List<Theatre> getAll() {
        return repository.findAll();
    }

    public Theatre save(Theatre theatre) {
        return repository.save(theatre);
    }
}