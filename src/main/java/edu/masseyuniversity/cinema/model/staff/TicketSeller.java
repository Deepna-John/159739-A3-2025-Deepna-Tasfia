package edu.masseyuniversity.cinema.model.staff;

/**
 * Represents a Ticket Seller staff member.
 * Can view and sell tickets but cannot manage movies.
 * Demonstrates inheritance and polymorphism.
 */
public class TicketSeller extends Staff {

    public TicketSeller(String username, String password) {
        super(username, password, "Ticket Seller");
    }

    @Override
    public boolean canManageMovies() {
        return false;
    }
}
