package com.moviebooking.movie.service;

import com.moviebooking.movie.entity.Movie;
import com.moviebooking.movie.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    public Movie createMovie(Movie movie) {
        return repository.save(movie);
    }
}