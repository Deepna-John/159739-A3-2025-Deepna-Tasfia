package edu.masseyuniversity.cinema.model;

/**
 * Represents a Romance movie with age restriction attribute.
 * Demonstrates inheritance and polymorphism.
 */
public class RomanceMovie extends Movie {
    private String ageRestriction;

    public RomanceMovie(String movieId, String title, String director, int duration,
                       double ticketPrice, String showTime, String ageRestriction, int availableTickets) {
        super(movieId, title, director, duration, ticketPrice, showTime, availableTickets, "Romance");
        this.ageRestriction = ageRestriction;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(String ageRestriction) {
        this.ageRestriction = ageRestriction;
    }

    @Override
    public String getExtraAttribute() {
        return ageRestriction;
    }

    @Override
    public void setExtraAttribute(String attribute) {
        this.ageRestriction = attribute;
    }
}
