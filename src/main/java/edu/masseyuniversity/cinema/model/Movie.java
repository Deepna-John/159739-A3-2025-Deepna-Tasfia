package edu.masseyuniversity.cinema.model;

/**
 * Abstract base class representing a movie in the cinema system.
 * Demonstrates encapsulation, abstraction, and provides foundation for inheritance.
 */
public abstract class Movie {
    private String movieId;
    private String title;
    private String director;
    private int duration;
    private double ticketPrice;
    private String showTime;
    private int availableTickets;
    private String category;

    /**
     * Constructor for Movie class
     */
    public Movie(String movieId, String title, String director, int duration, 
                 double ticketPrice, String showTime, int availableTickets, String category) {
        this.movieId = movieId;
        this.title = title;
        this.director = director;
        this.duration = duration;
        this.ticketPrice = ticketPrice;
        this.showTime = showTime;
        this.availableTickets = availableTickets;
        this.category = category;
    }

    // Getters
    public String getMovieId() {
        return movieId;
    }

    public String getTitle() {
        return title;
    }

    public String getDirector() {
        return director;
    }

    public int getDuration() {
        return duration;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public String getShowTime() {
        return showTime;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public String getCategory() {
        return category;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }

    /**
     * Sells a ticket by reducing the available ticket count.
     * @throws IllegalStateException if no tickets are available
     */
    public void sellTicket() throws IllegalStateException {
        if (availableTickets <= 0) {
            throw new IllegalStateException("No tickets available for " + title);
        }
        availableTickets--;
    }

    /**
     * Abstract method to get extra attribute specific to movie type.
     * This demonstrates polymorphism - each subclass will override this.
     */
    public abstract String getExtraAttribute();

    /**
     * Abstract method to set extra attribute specific to movie type.
     */
    public abstract void setExtraAttribute(String attribute);

    /**
     * Returns a formatted string for file export.
     */
    public String toFileString() {
        return String.format("%s, %s, %s, %s, %d, %.1f, %s, %s, %d",
                category, movieId, title, director, duration, ticketPrice, 
                showTime, getExtraAttribute(), availableTickets);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", movieId, title, showTime);
    }
} 