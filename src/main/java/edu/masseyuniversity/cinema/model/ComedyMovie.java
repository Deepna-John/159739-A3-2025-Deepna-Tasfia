package edu.masseyuniversity.cinema.model;

/**
 * Represents a Comedy movie.
 * Demonstrates inheritance and polymorphism.
 */
public class ComedyMovie extends Movie {

    public ComedyMovie(String movieId, String title, String director, int duration,
                      double ticketPrice, String showTime, int availableTickets) {
        super(movieId, title, director, duration, ticketPrice, showTime, availableTickets, "Comedy");
    }

    @Override
    public String getExtraAttribute() {
        return "-";
    }

    @Override
    public void setExtraAttribute(String attribute) {
        // Comedy movies don't have extra attributes
    }
}