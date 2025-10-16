package edu.masseyuniversity.cinema.model;

/**
 * Represents an Action movie with stunt level attribute.
 * Demonstrates inheritance and polymorphism.
 */
public class ActionMovie extends Movie {
    private String stuntLevel;

    public ActionMovie(String movieId, String title, String director, int duration,
                      double ticketPrice, String showTime, String stuntLevel, int availableTickets) {
        super(movieId, title, director, duration, ticketPrice, showTime, availableTickets, "Action");
        this.stuntLevel = stuntLevel;
    }

    public String getStuntLevel() {
        return stuntLevel;
    }

    public void setStuntLevel(String stuntLevel) {
        this.stuntLevel = stuntLevel;
    }

    @Override
    public String getExtraAttribute() {
        return stuntLevel;
    }

    @Override
    public void setExtraAttribute(String attribute) {
        this.stuntLevel = attribute;
    }
}