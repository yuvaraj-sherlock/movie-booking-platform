package com.moviebooking.movie.controller;

import com.moviebooking.movie.entity.Movie;
import com.moviebooking.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService service;

    @GetMapping
    public List<Movie> getMovies() {
        return service.getAllMovies();
    }

    @PostMapping
    public Movie addMovie(@RequestBody Movie movie) {
        return service.createMovie(movie);
    }
}