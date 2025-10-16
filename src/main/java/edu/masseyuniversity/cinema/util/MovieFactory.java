package edu.masseyuniversity.cinema.util;

import edu.masseyuniversity.cinema.model.*;

/**
 * Factory class for creating movie objects based on category.
 * Demonstrates Factory design pattern and polymorphism.
 */
public class MovieFactory {

    /**
     * Creates a movie object based on the category
     * @param parts Array containing movie data from file
     * @return Movie object of appropriate type
     * @throws IllegalArgumentException if category is invalid
     */
    public static Movie createMovie(String[] parts) throws IllegalArgumentException {
        if (parts.length < 9) {
            throw new IllegalArgumentException("Invalid movie data format");
        }

        String category = parts[0].trim();
        String movieId = parts[1].trim();
        String title = parts[2].trim();
        String director = parts[3].trim();
        int duration = Integer.parseInt(parts[4].trim());
        double price = Double.parseDouble(parts[5].trim());
        String showTime = parts[6].trim();
        String extraAttr = parts[7].trim();
        int availableTickets = Integer.parseInt(parts[8].trim());

        switch (category) {
            case "Action":
                return new ActionMovie(movieId, title, director, duration, price, 
                                      showTime, extraAttr, availableTickets);
            case "Comedy":
                return new ComedyMovie(movieId, title, director, duration, price, 
                                      showTime, availableTickets);
            case "Romance":
                return new RomanceMovie(movieId, title, director, duration, price, 
                                       showTime, extraAttr, availableTickets);
            case "ScienceFiction":
                return new ScienceFictionMovie(movieId, title, director, duration, price, 
                                              showTime, extraAttr, availableTickets);
            default:
                throw new IllegalArgumentException("Unknown movie category: " + category);
        }
    }
}