package edu.masseyuniversity.cinema.model;

/**
 * Represents a Science Fiction movie with IMAX or 3D flag.
 * Demonstrates inheritance and polymorphism.
 */
public class ScienceFictionMovie extends Movie {
    private String format; // IMAX or 3D

    public ScienceFictionMovie(String movieId, String title, String director, int duration,
                              double ticketPrice, String showTime, String format, int availableTickets) {
        super(movieId, title, director, duration, ticketPrice, showTime, availableTickets, "ScienceFiction");
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public String getExtraAttribute() {
        return format;
    }

    @Override
    public void setExtraAttribute(String attribute) {
        this.format = attribute;
    }
} 
