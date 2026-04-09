package com.moviebooking.theatre.controller;

import com.moviebooking.theatre.entity.Theatre;
import com.moviebooking.theatre.service.TheatreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/theatres")
public class TheatreController {

    @Autowired
    private TheatreService service;

    @GetMapping
    public List<Theatre> getAll() {
        return service.getAll();
    }

    @PostMapping
    public Theatre create(@RequestBody Theatre theatre) {
        return service.save(theatre);
    }
}