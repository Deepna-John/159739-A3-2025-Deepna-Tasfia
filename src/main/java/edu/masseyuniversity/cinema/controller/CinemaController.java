package edu.masseyuniversity.cinema.controller;

import edu.masseyuniversity.cinema.model.*;
import edu.masseyuniversity.cinema.util.MovieFileHandler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CinemaController {
    private List<Movie> movies;

    public CinemaController() {
        movies = new ArrayList<>();
    }

    public void loadMovies(String filename) throws IOException {
        movies = MovieFileHandler.loadMovies(filename);
    }

    public void exportMovies(String filename) throws IOException {
        MovieFileHandler.exportMovies(movies, filename);
    }

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies);
    }

    public List<Movie> searchByCategory(String category) {
        if (category == null || category.equals("All")) {
            return getAllMovies();
        }
        return movies.stream()
                .filter(m -> m.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public List<Movie> searchByCategoryAndTitle(String category, String title) {
        List<Movie> categoryMovies = searchByCategory(category);
        if (title == null || title.trim().isEmpty()) {
            return categoryMovies;
        }
        String searchTitle = title.trim().toLowerCase();
        return categoryMovies.stream()
                .filter(m -> m.getTitle().toLowerCase().contains(searchTitle))
                .collect(Collectors.toList());
    }

    public Movie findMovieById(String movieId) {
        return movies.stream()
                .filter(m -> m.getMovieId().equals(movieId))
                .findFirst()
                .orElse(null);
    }

    public void addMovie(Movie movie) throws IllegalArgumentException {
        if (findMovieById(movie.getMovieId()) != null) {
            throw new IllegalArgumentException("Movie ID already exists: " + movie.getMovieId());
        }
        movies.add(movie);
    }

    public void updateMovie(Movie updatedMovie) throws IllegalArgumentException {
        Movie existing = findMovieById(updatedMovie.getMovieId());
        if (existing == null) {
            throw new IllegalArgumentException("Movie not found: " + updatedMovie.getMovieId());
        }
        int index = movies.indexOf(existing);
        movies.set(index, updatedMovie);
    }

    public void deleteMovie(String movieId) throws IllegalArgumentException {
        Movie movie = findMovieById(movieId);
        if (movie == null) {
            throw new IllegalArgumentException("Movie not found: " + movieId);
        }
        movies.remove(movie);
    }

    public void sellTicket(String movieId) throws IllegalStateException {
        Movie movie = findMovieById(movieId);
        if (movie == null) {
            throw new IllegalStateException("Movie not found");
        }
        movie.sellTicket();
    }
}